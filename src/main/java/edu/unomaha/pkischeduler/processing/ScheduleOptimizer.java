package edu.unomaha.pkischeduler.processing;

import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.CourseChange;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CRIService;
import edu.unomaha.pkischeduler.data.service.CourseChangeService;
import edu.unomaha.pkischeduler.ui.EditView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ScheduleOptimizer
{
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleOptimizer.class);
    CRIService service;
    CourseChangeService courseChangeService;
    public ScheduleOptimizer(CRIService service, CourseChangeService courseChangeService )
    {
        this.courseChangeService = courseChangeService;
        this.service = service;
    }

    /**
     * Assigns rooms naively without regard for optimal placement.
     * Only considers the smallest possible room.
     * Cross listings are decided upon the "Also collegexxxx-xxx"
     *  and uses the "see" terminology to map into the "also"
     *  so it does not handle any other formats for cross-listings.
     * Does not consider cross-listings outside of dataset.
     * Does not refactor.
     */
    public void naive_assignment()
    {
        List<Room> rooms = service.getAllRooms();
        List<Course> courses = service.getAllCourses();

        //reorder to allow lab classes to be placed first
        List<Course> factoredCourses = new ArrayList<>();
        for (Course course : courses)
        {
            if (course.getSectionType().equals("Laboratory"))
                factoredCourses.add(course);
        }
        for (Course course : courses)
        {
            if (!course.getSectionType().equals("Laboratory"))
                factoredCourses.add(course);
        }

        for (Course course : factoredCourses)
        {
            List<Course> crossListings = new ArrayList<>();
            if (course.getRoom().getNumber() == 0) //if unassigned
            {
                int workingEnrollment = course.getExpectedAsInt();
                if (!course.getCrossListings().equals("None"))//handle cross listings
                {
                    if (course.getCrossListings().charAt(0) == 'S') continue; //ignore "See" listings 😎 you can force a failure by making workingEnrollment > 80 here
                    crossListings = this.parseCrossListings(course.getCrossListings(), courses);
                    for (Course listing : crossListings)
                    {
                        workingEnrollment += listing.getExpectedAsInt();
                    }
                }
                ArrayList<Room> viableRooms = new ArrayList<>();
                for (Room room : rooms)
                {
                    if (room.getNumber() != -1 && room.getCapacity() >= workingEnrollment)
                    {
                        boolean flag = true; //flag to test if there is a conflict
                        List<Course> roomCourses = room.getCourses();
                        for (Course assigned : roomCourses)
                        {
                            if (!this.checkForConflict(course, assigned))
                            {
                                flag = false;
                                break;
                            }
                        }
                        //check if room is viable and handle if it needs to be a lab
                        if (flag && course.getSectionType().equals("Laboratory") && room.getRoomType().equals("Laboratory"))
                            viableRooms.add(room);
                        else if (flag && !course.getSectionType().equals("Laboratory"))
                            viableRooms.add(room);
                    }
                }
                //find smallest viable room
                Room placement = null;
                for (Room room : viableRooms)
                {
                    if (placement != null)
                    {
                        if (room.getCapacity() < placement.getCapacity())
                        {
                            placement = room;
                        }
                    }
                    else placement = room;
                }
                if (placement != null)
                {
                    course.setRoom(placement);
                    placement.addCourse(course);
                    service.update(course);
                    for (Course listing : crossListings)
                    {
                        listing.setRoom(placement);
                        placement.addCourse(listing);
                        service.update(listing);
                    }
                }
                else
                {
                    LOG.error("No viable room found for course: " + course.toStringForLog() ) ;
                    CourseChange courseChange = new CourseChange();
                    courseChange.setLogMessage( "No viable room found for course: " + course.toStringForLog()       );
                    courseChangeService.add(courseChange);
                }


            }
        }
    }

    /**
     * Determines if there is a timing conflict between two courses.
     * @param a the first course (unassigned) to be compared
     * @param b the second course (preassigned) to be compared
     * @return true if there is no conflict between the courses
     */
    private boolean checkForConflict(Course a, Course b)
    {
        ArrayList<String> daysA = this.parseDays(a.getMeetingDays());
        int[] timesA = this.parseTime(a.getMeetingTime());
        ArrayList<String> daysB = this.parseDays(b.getMeetingDays());
        int[] timesB = this.parseTime(b.getMeetingTime());
        boolean dayFlag = false;
        boolean ret = true;

        //check for same days
        for (String day : daysA)
        {
            if (!day.equals(" "))
                if (daysB.contains(day))
                {
                    dayFlag = true;
                    break;
                }
        }


        if (dayFlag)
        {
            //convert both to military time
            // if time is pm add 12 to hours
            if (timesA[2] == 1 && timesA[0] != 12) timesA[0]+=12;
            if (timesA[5] == 1 && timesA[3] != 12) timesA[3]+=12;

            //do the same for b
            if (timesB[2] == 1 && timesB[0] != 12) timesB[0]+=12;
            if (timesB[5] == 1 && timesB[3] != 12) timesB[3]+=12;

            //test hours for conflict, if so test minutes with 15 min grace
            if (timesA[0] >= timesB[0] && timesA[0] <= timesB[3]) //if b.start <= a.start <= b.end
            {
                if (!(timesA[0] == timesB[3] && timesA[1] >= timesB[4]+10)) //if a does not start 15 min after b ends
                    ret = false;
            }
            else if (timesA[3] >= timesB[0] && timesA[3] <= timesB[3]) //if b.start <= a.end <= b.end
            {
                if (!(timesA[3] == timesB[0] && timesA[4] <= timesB[1]-10)) //if a does not end 15 min before b starts
                    ret = false;
            }
            else if (timesA[0] <= timesB[0] && timesA[3] >= timesB[3]) //if a.start <= b <= a.end
            {
                ret = false;
            }
        }
        else
        {
            return true;
        }
        return ret;
    }

    /**
     * Helper method to parse the days item into individuals
     * @param in Days string
     * @return An ArrayList of the individual meeting days
     */
    private ArrayList<String> parseDays(String in)
    {
        ArrayList<String> ret = new ArrayList<>();
        char[] work = in.toCharArray();
        for (int i = 0; i < work.length; i++)
        {
            if (i+1 < work.length && work[i] == 'T' && work[i+1] == 'h')
            {
                ret.add("Th");
                i++;
            }
            else if (i+1 < work.length && work[i] == 'S')
            {
                ret.add("Sa");
                i++;
            }
            else
            {
                ret.add(String.valueOf(work[i]));
            }
        }
        return ret;
    }

    /**
     * Parses time into an array with each element corresponding to a particular notation.
     * Sort of a pseudo-object.
     * [0] = beginning hour,
     * [1] = beginning minute,
     * [2] = beginning time of day 0 = am 1 = pm,
     * [3] = ending hour,
     * [4] = ending minute,
     * [5] = ending time of day 0 = am 1 = pm.
     * @param in String of the form 00:00am-00:00pm
     * @return int[] with given schema
     */
    private int[] parseTime(String in)
    {
        int[] ret = new int[6];
        char[] work = in.toCharArray();

        //find beginning hour
        int i;
        boolean flag = false;
        String hold = "";
        for (i = 0; work[i] != ':'; i++)
        {
            if (work[i] == 'a' || work[i] == 'p')
            {
                flag = true;
                break;
            }
            hold += work[i];
        }
        ret[0] = Integer.parseInt(hold);

        //find beginning minute
        if (flag) ret[1] = 0;
        else
        {
            hold = "" + work[++i] + work[++i];
            ret[1] = Integer.parseInt(hold);
            i++;
        }

        //find am or pm
        if (work[i] == 'a')
            ret[2] = 0;
        else
            ret[2] = 1;

        //find ending hour
        flag = false;
        hold = "";
        for (i = i+3; work[i] != ':'; i++)
        {
            if (work[i] == 'a' || work[i] == 'p')
            {
                flag = true;
                break;
            }
            hold += work[i];
        }
        ret[3] = Integer.parseInt(hold);

        //find ending minute
        if (flag) ret[4] = 0;
        else
        {
            hold = "" + work[++i] + work[++i];
            ret[4] = Integer.parseInt(hold);
            i++;
        }

        //find ending am or pm
        if (work[i] == 'a')
            ret[5] = 0;
        else
            ret[5] = 1;

        return ret;
    }

    /**
     * Parses the cross listing string into its component courses.
     * @param in The String containing all cross listings.
     * @param courses List of all courses to search through.
     * @return A list of the courses that match the cross listing string.
     */
    private ArrayList<Course> parseCrossListings(String in, List<Course> courses) {
        List<String> courseTitles = new ArrayList<>();
        List<String> sectionNums = new ArrayList<>();
        ArrayList<Course> ret = new ArrayList<>();
        char[] parse = in.toCharArray();
        int i = 4;

        //for (i = 0; parse[i] != ' '; i++) {}//skip "See" or "Also"

        boolean multiple = false;
        do //parse out the course titles and section ids
        {
            multiple = false;
            String work = "";
            for (i = i + 1; parse[i] != '-'; i++)//get title
            {
                work += parse[i];
            }
            courseTitles.add(work);

            work = "";
            for (i = i + 1; i < parse.length; i++)//get id
            {
                if (parse[i] == ',')
                {
                    multiple = true;
                    break;
                }
                work += parse[i];
            }
            sectionNums.add(work);
            i++;
        } while(multiple);


        //now to do course lookup
        for (int j = 0; j < courseTitles.size(); j++)
        {
            String code = courseTitles.get(j);
            String section = sectionNums.get(j);
            for (Course course : courses)
            {
                if (course.getCourseCode().equals(code) && course.getSectionNumber().equals(section))
                {
                    ret.add(course);
                    break;
                }
            }
        }

        return ret;
    }
}

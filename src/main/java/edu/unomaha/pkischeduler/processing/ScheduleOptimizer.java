package edu.unomaha.pkischeduler.processing;

import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScheduleOptimizer {
    CourseService service;
    public ScheduleOptimizer(CourseService service)
    {
        this.service = service;
    }

    public void backtracking_search()
    {

        List<Room> rooms = service.getAllRooms();
        List<Course> courses = service.getAllCourses();
        Random randy = new Random();
        for (Course course : courses)
        {
            if (course.getRoom().getNumber() == 0)
            {
                ArrayList<Room> viableRooms = new ArrayList<Room>();
                for (Room room : rooms)
                {

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
            if (timesA[2] == 1) timesA[0]+=12;
            if (timesA[5] == 1) timesA[3]+=12;

            //do the same for b
            if (timesB[2] == 1) timesB[0]+=12;
            if (timesB[5] == 1) timesB[3]+=12;

            //test hours for conflict, if so test minutes with 15 min grace
            if (timesA[0] >= timesB[0] && timesA[0] <= timesB[3]) //if b.start <= a.start <= b.end
            {
                if (!(timesA[0] == timesB[3] && timesA[1] >= timesB[4]+15)) //if a does not start 15 min after b ends
                    ret = false;
            }
            else if (timesA[3] >= timesB[0] && timesA[3] <= timesB[3]) //if b.start <= a.end <= b.end
            {
                if (!(timesA[3] == timesB[0] && timesA[1] <= timesB[4]-15)) //if a does not end 15 min before b starts
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
        ArrayList<String> ret = new ArrayList<String>();
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
        String hold = "";
        for (i = 0; work[i] != ':'; i++)
        {
            hold += work[i];
        }
        ret[0] = Integer.parseInt(hold);

        //find beginning minute
        hold = "" + work[++i] + work[++i];
        ret[1] = Integer.parseInt(hold);

        //find am or pm
        if (work[++i] == 'a')
            ret[2] = 0;
        else
            ret[2] = 1;
        i+=3;

        //find ending hour
        hold = "";
        for (i = i; work[i] != ':'; i++)
        {
            hold += work[i];
        }
        ret[3] = Integer.parseInt(hold);

        //find ending minute
        hold = "" + work[++i] + work[++i];
        ret[4] = Integer.parseInt(hold);

        //find ending am or pm
        if (work[++i] == 'a')
            ret[5] = 0;
        else
            ret[5] = 1;

        return ret;
    }

    /*
    public void backtracking_search()
    {
        List<Room> rooms = service.getAllRooms();
        List<Course> courses = service.getAllCourses();
        Random randy = new Random();
        for (Course course : courses)
        {
            course.setRoom(rooms.get(randy.nextInt(23-1)+1));
            service.update(course);
        }
    }
    */
}

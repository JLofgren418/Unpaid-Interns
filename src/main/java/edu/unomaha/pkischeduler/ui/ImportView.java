package edu.unomaha.pkischeduler.ui;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CRIService;
import edu.unomaha.pkischeduler.data.service.CourseChangeService;
import edu.unomaha.pkischeduler.processing.ScheduleOptimizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ImportView creates the UI for the Import page.
 * This UI is designed to allow users to upload
 *  data into the system and to assign courses to rooms.
 */
@Route(value = "")
@PageTitle("Import")
public class ImportView extends AppLayout {

    /**
     * A grid component containing courses for UI.
     */
    Grid<Course> grid = new Grid<>(Course.class);

    /**
     * A text field component that allows the user to filter the grid.
     */
    TextField filterText = new TextField();

    /**
     * Service provider which allows the application to access
     * the repositories for Courses, Rooms, and Instructors (CRI)
     */
    CRIService service;


    /**
     * MemoryBuffer for file uploading.
     */
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

    /**
     * Upload component which allows the user to import
     * files into the application.
     */
    Upload upload = new Upload(buffer);

    /**
     * A button component which assigns courses to rooms and
     * then refreshes the grid.
     */
    Button process = new Button("Process Schedule", VaadinIcon.FILE_PROCESS.create());

    /**
     * The schedule optimizer which assigns courses to rooms.
     */
    ScheduleOptimizer optimizer;
    CourseChangeService courseChangeService;


    /**
     * Import view calls the necessary methods to create the UI.
     * @param service A service class used to access the course, room,
     *                 and instructor tables in the database.
     */
    public ImportView(CRIService service, CourseChangeService courseChangeService){
        this.service = service;
        this.courseChangeService = courseChangeService;
        optimizer = new ScheduleOptimizer(service , courseChangeService);
        addClassName("import-view");
        configureGrid();
        importCSV();
        setContent(getImportContent());
        addToNavbar(getTabs());
        updateList();
        process.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        process.addClickListener(click ->
                optimizer.naive_assignment()); //Assign classrooms
        process.addClickListener(click -> grid.setItems(service.getAllCourses()));
    }


    /**
     * This function compiles the toolbar and
     * the grid into a vertical layout which is then
     * added to the UI.
     * @return The content containing the grid and the toolbar
     *  in a vertical layout.
     */
    private Component getImportContent() {
        VerticalLayout content = new VerticalLayout(getToolbar(),grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    /**
     * This function populates the grid with the necessary fields from
     *  items in the database.
     */
    private void configureGrid() {
        grid.addClassNames("schedule-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
        grid.addColumn(Course::getSectionNumber).setHeader("Section");
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title");
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days");
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time");
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Instructor");
        grid.addColumn(Course::getExpectedAsInt).setHeader("Maximum Enrollment");
        grid.addColumn(course -> course.getRoom().getCapacity()).setHeader("Room Capacity");
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    /**
     * This function provides the navigation tabs in a horizontal
     *  layout that is added to the navigation bar.
     * @return A horizontal layout containing the navigation tabs.
     */
    public HorizontalLayout getTabs() {

        Tab redirect1 = new Tab(VaadinIcon.UPLOAD.create());
        redirect1.add(new RouterLink("Upload", ImportView.class));
        redirect1.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        Tab redirect2 = new Tab(VaadinIcon.PENCIL.create());
        redirect2.add(new RouterLink("Edit", EditView.class));
        redirect2.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        Tab redirect3 = new Tab(VaadinIcon.EYE.create());
        redirect3.add(new RouterLink("Room", RoomView.class));
        redirect3.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        Tab redirect4 = new Tab(VaadinIcon.DOWNLOAD.create());
        redirect4.add(new RouterLink("Export", ExportView.class));
        redirect4.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        HorizontalLayout h1 = new HorizontalLayout(redirect1,redirect2,redirect3,redirect4);

        return h1;
    }

    /**
     * This function compiles a horizontal toolbar that contains the
     * grid filter, the data processing button, and the file upload field.
     * @return horizontal layout containing the grid filter, the import field,
     *  and the data processing button.
     */
    private HorizontalLayout getToolbar()
    {
        filterText.setPlaceholder("Filter by course code");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        HorizontalLayout h1 = new HorizontalLayout(filterText);
        h1.setWidth("33%");
        h1.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        HorizontalLayout h2 = new HorizontalLayout(process);
        h2.setWidth("33%");
        h2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        HorizontalLayout h3 = new HorizontalLayout(upload);
        h3.setWidth("33%");
        h3.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        HorizontalLayout h4 = new HorizontalLayout(h1,h2,h3);
        h4.setWidth("100%");
        h4.setAlignItems(FlexComponent.Alignment.CENTER);
        h4.setFlexGrow(1, h3);

        return h4;
    }

    /**
     * This method updates the grid based on the text
     *  that the user has input in the filter.
     * The service class is accessed, and a custom query
     *  in the repository is used to search the table based on user input.
     */
    private void updateList() {

       grid.setItems(service.filterCourses(filterText.getValue()));
    }


    /**
     * This function creates an event listener that allows a file upload to begin.
     * Once a file is received successfully, an input stream is created to process the
     *  contents.
     */
    private void importCSV()
    {
        upload.setAcceptedFileTypes(".csv");

        upload.addSucceededListener(event -> {
            // Determine which file was uploaded
            String fileName = event.getFileName();
            //upload.setMaxFileSize(1);
            // Get input stream specifically for the finished file
            InputStream fileData = buffer
                    .getInputStream(fileName);
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            // Do something with the file data
            processCSV(fileData);
        });
    }


    /**
     * This function uses a CSV parser to quickly and easily extract fields from the CSV files.
     * The function goes through each line of the csv, creates objects from csv data,
     *  and stores the created objects in the database.
     * @param fileData An input stream containing the data of the imported file.
     */
    private void processCSV(InputStream fileData)
    {
        var parser = new CSVParserBuilder().withSeparator(',').build();
        try (var reader = new CSVReaderBuilder(new InputStreamReader(fileData)).withCSVParser(parser).build()) {
            List<String[]> entries = reader.readAll();

            int numRows = (int) reader.getRecordsRead();

            for (int i = 4; i < numRows; i++)//for each row starting at 5
            {
                String[] row = entries.get(i);
                //System.out.println(i);
                if (row[0].equals(""))//Find rows to process
                {
                    //translate to csv column name
                    String classID = row[1];
                    String sisID = row[2];
                    String term = row[3];
                    String term_code = row[4];
                    String deptCode = row[5];
                    String subjectCode = row[6];
                    String catalogNumber = row[7];
                    String courseCode = row[8];
                    String sectionNumber = row[9];
                    String courseTitle = row[10];
                    String sectionType = row[11];
                    String title = row[12];
                    String meetingPattern = row[13];
                    String instructorName = row[14];
                    String room = row[15];
                    String status = row[16];
                    String session = row[17];
                    String campus = row[18];
                    String instructionMethod = row[19];
                    String partner = row[20];
                    String printable = row[21];
                    String consent = row[22];
                    String minCredits = row[23];
                    String credits = row[24];
                    String graded = row[25];
                    String attributes = row[26];
                    String roomAttributes = row[27];
                    String enrollment = row[28];
                    String maxEnrollment = row[29];
                    String priorEnrollment = row[30];
                    String waitCap = row[32];
                    String roomCap = row[33];
                    String crossListings = row.length>34?row[34]:"";
                    String link = row.length>35?row[35]:"";
                    String comments =row.length>36?row[36]:"";
                    String notes = row.length>37?row[37]:"";

                    if (!meetingPattern.equals("Does Not Meet") && !status.equals("Cancelled")
                            && !instructionMethod.equals("Totally Online"))//Filter out online and cancelled courses
                    {
                        //Process variables before adding them to a new object;
                        if (crossListings.equals("")) {
                            crossListings = "None";
                        }
                        double expectedEnrollment = Double.parseDouble(maxEnrollment);
                        instructorName = instructorName.replaceAll("[^.,a-zA-Z]", " ");
                        instructorName = instructorName.trim();
                        String meetingDays;
                        String meetingTime;//set meeting time and days from meeting pattern
                        int ndex;
                        for (ndex = 0; meetingPattern.charAt(ndex) != ' '; ndex++)
                        {/*get index of the space*/}
                        meetingDays = meetingPattern.substring(0, ndex);
                        meetingTime = meetingPattern.substring(ndex+1);


                        //Make new objects from csv line
                        Room defaultRoom = service.getDefaultRoom();

                        Instructor instructor = service.exstingInstructor(instructorName);

                        if (instructor == null) {
                            instructor = new Instructor(instructorName, "Any");
                            //instructors.add(instructor);
                            service.addInstructor(instructor);
                        }

                        Course course = new Course(consent, credits, minCredits, graded, attributes,  roomAttributes,  enrollment,  maxEnrollment,  classID,
                                sisID,  term,  term_code,  deptCode,  subjectCode,  catalogNumber,  courseCode,  courseTitle,  sectionType,
                                meetingDays,  meetingTime,  priorEnrollment,  title,  partner,  waitCap,  roomCap,  link,
                                comments,  notes,  crossListings, expectedEnrollment,  session,  campus,  sectionNumber,  status,  instructionMethod,
                        printable,  defaultRoom, instructor);

                        service.add(course);

                    }
                    else //process classes that don't need rooms
                    {
                        //Process variables before adding them to a new object;
                        if (crossListings.equals("")) {
                            crossListings = "None";
                        }
                        double expectedEnrollment = Double.parseDouble(maxEnrollment);
                        instructorName = instructorName.replaceAll("[^.,a-zA-Z]", " ");
                        instructorName = instructorName.trim();
                        String meetingDays = "None";
                        String meetingTime = "Does Not Meet";

                        //acquire room -1 and save into defaultRoom
                        List<Room> rooms = service.getAllRooms();
                        Room defaultRoom = null; //initialize to null
                        for (Room rm : rooms)
                        {
                            if (rm.getNumber() == -1)
                            {
                                defaultRoom = rm;
                                break;
                            }
                        }

                        Instructor instructor = service.exstingInstructor(instructorName);

                        if (instructor == null) {
                            instructor = new Instructor(instructorName, "Any");
                            //instructors.add(instructor);
                            service.addInstructor(instructor);
                        }

                        Course course = new Course(consent, credits, minCredits, graded, attributes,  roomAttributes,  enrollment,  maxEnrollment,  classID,
                                sisID,  term,  term_code,  deptCode,  subjectCode,  catalogNumber,  courseCode,  courseTitle,  sectionType,
                                meetingDays,  meetingTime,  priorEnrollment,  title,  partner,  waitCap,  roomCap,  link,
                                comments,  notes,  crossListings, expectedEnrollment,  session,  campus,  sectionNumber,  status,  instructionMethod,
                                printable,  defaultRoom, instructor);

                        service.add(course);

                    }

                }

            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        grid.setItems(service.getAllCourses());
    }

}
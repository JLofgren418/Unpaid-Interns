package edu.unomaha.pkischeduler.ui;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CourseService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Route(value = "")
@PageTitle("Import")
public class ImportView extends VerticalLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    CourseService service;
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);

    Button process = new Button("Process Schedule");


    public ImportView(CourseService service) {
        this.service = service;
        addClassName("import-view");
        setSizeFull();
        configureGrid();
        importCSV();
        add(getToolbar(), getContent());
        updateList();
        process.addClickListener(click -> grid.setItems(service.getCourses()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

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
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by course code");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button redirect1 = new Button("Go to edit");
        redirect1.addClickListener(e ->
                redirect1.getUI().ifPresent(ui ->
                        ui.navigate("edit")));

        Button redirect2 = new Button("Go to export");
        redirect2.addClickListener(e ->
                redirect2.getUI().ifPresent(ui ->
                        ui.navigate("export")));

        HorizontalLayout h1 = new HorizontalLayout(redirect1, redirect2, process, upload);
        h1.setAlignItems(Alignment.BASELINE);

        HorizontalLayout toolbar = new HorizontalLayout(filterText,h1);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.setWidth("100%");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }

    private void updateList() {

       grid.setItems(service.findAllCourses(filterText.getValue()));
    }

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
            processCSV(fileData, fileName, contentLength, mimeType);
        });
    }

    //This method goes through each line of the csv,
    // creates objects from csv data,
    // and places objects in an array
    private void processCSV(InputStream fileData, String fileName, Long contentLength, String mimeType)
    {
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(fileData)).withCSVParser(parser).build();

        try{
            List<String[]> entries  = reader.readAll();

            int numRows = (int) reader.getRecordsRead();

            for(int i = 4; i < numRows; i++)//for each row starting at 5
            {
                String[] row = entries.get(i);
                //System.out.println(i);
                if(row[0] == "")//Find rows to process
                {
                    //translate to csv column name
                    String meetingPattern = row[13];
                    String status = row[16];
                    String instructionMethod = row[19];
                    String courseCode = row[8];
                    String sectionNumber = row[9];
                    String courseTitle = row[10];
                    String sectionType = row[11];
                    String instructorName = row[14];
                    String maxEnrollment = row[29];
                    String crossListings = row[34];

                    if(!meetingPattern.equals("Does Not Meet") && !status.equals("Cancelled")
                    && !instructionMethod.equals("Totally Online"))//Filter out online and cancelled courses
                    {
                        //Process variables before adding them to a new object;
                        if(crossListings.equals("")) {crossListings = "None";}
                        double expectedEnrollment = Double.parseDouble(maxEnrollment);
                        instructorName = instructorName.replaceAll("[^.,a-zA-Z]", " ");
                        instructorName = instructorName.trim();
                        String meetingDays;
                        String meetingTime;//set meeting time and days from meeting pattern
                        if(meetingPattern.charAt(2) != 'h') {
                            meetingDays = meetingPattern.substring(0, 2);
                            meetingTime = meetingPattern.substring(2);
                            if (meetingTime.charAt(0) == ' ') {
                                meetingTime = meetingTime.substring(1);
                            }
                        }
                        else
                        {
                            meetingDays = meetingPattern.substring(0, 3);
                            meetingTime = meetingPattern.substring(3);
                            if (meetingTime.charAt(0) == ' ') {
                                meetingTime = meetingTime.substring(1);
                            }
                        }

                        //Make new objects from csv line
                        Room defaultRoom = service.getDefaultRoom();

                        Instructor instructor = service.exstingInstructor(instructorName);

                        if(instructor == null)
                        {
                            instructor = new Instructor(instructorName, "Any");
                            //instructors.add(instructor);
                            service.addInstructor(instructor);
                        }

                        Course course = new Course(courseCode, courseTitle, sectionType,
                                meetingDays, meetingTime, crossListings, expectedEnrollment,
                                sectionNumber, instructor, defaultRoom);

                        service.add(course);

                    }

                }

            }

        } catch (IOException | CsvException e)
        {
            e.printStackTrace();
        }
        grid.setItems(service.getCourses());
    }

}
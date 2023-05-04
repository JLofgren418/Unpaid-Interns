package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.service.CRIService;
import edu.unomaha.pkischeduler.data.service.CourseChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ExportView creates the UI for the Export page.
 * The UI is designed to allow the user to export the data
 *  from the system after processing, editing and verifying.
 */
@Route(value = "export")
@PageTitle("Export")
public class ExportView extends AppLayout {
    private static final Logger LOG = LoggerFactory.getLogger(ExportView.class);
    /**
     * A grid component containing courses.
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


    /** A button that allows the user to download the log file. */
    Button downloadLogBtn;


    /**  Used to generate downloadable log    */
    CourseChangeService courseChangeService;


    /**
     * ExportView calls the necessary methods to create the UI.
     * @param service A service class used to access the course, room,
     *                 and instructor tables in the database.
     */
    public ExportView(CRIService service, CourseChangeService courseChangeService) {
        this.service = service;
        this.courseChangeService = courseChangeService;
        addClassName("export-view");
        configureGrid();
        setContent(getExportContent());
        addToNavbar(getTabs());
        updateList();
    }


    /**
     * This function compiles the toolbar and
     * the grid into a vertical layout which is then
     * added to the UI.
     * @return The content containing the grid and the toolbar
     *  in a vertical layout.
     */
    private Component getExportContent() {
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
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code").setSortable(true);
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title").setSortable(true);
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days").setSortable(true);
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time").setSortable(true);
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Instructor").setSortable(true);
        grid.addColumn(Course::getExpectedAsInt).setHeader("Maximum Enrollment").setSortable(true);
        grid.addColumn(course -> course.getRoom().getCapacity()).setHeader("Room Capacity").setSortable(true);
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    /**
     * This function provides the navigation tabs in a horizontal
     *  layout that is added to the navigation bar.
     * @return A horizontal layout containing the navigation tabs.
     */
    private HorizontalLayout getTabs() {

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
     * This function compiles a horizontal toolbar that contains
     *  both the grid filter and the export button.
     * @return horizontal layout containing the grid filter and the
     *  export button.
     */
    private HorizontalLayout getToolbar()
    {
        filterText.setPlaceholder("Filter by course code");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        HorizontalLayout filterLayout = new HorizontalLayout(filterText);
        filterLayout.setWidth("35%");


      // ADD download log button
        StreamResource streamResource = new StreamResource(  "CourseChange.log", () ->
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            courseChangeService.exportLog(outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return inputStream;
        }   );

        // download Log button
        Anchor downloadLogAnchor = new Anchor(streamResource, "download log file");
        downloadLogAnchor.getElement().setAttribute("download", true);
        downloadLogAnchor.getStyle().set("display", "none");

        downloadLogBtn = new Button("Download Log",VaadinIcon.DOWNLOAD.create());
        downloadLogBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        downloadLogBtn.getElement().setProperty("title", "Download log file");
        downloadLogBtn.addClickListener(e -> {
                    LOG.trace("Download log button clicked");
                    downloadLogAnchor.getElement().executeJs("this.click()");
        });

        // CSV
        var exportCSVbttn = new Button("Export CSV",VaadinIcon.DOWNLOAD.create());
        exportCSVbttn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        var streamReasource = new StreamResource("schedule.csv",
                () -> {
            return new ByteArrayInputStream(prepareCSV().getBytes());
                });
        var exportCSV_Anchor = new Anchor(streamReasource, "Export CSV");
        exportCSV_Anchor.getStyle().set("display", "none");

        exportCSVbttn.addClickListener(e -> {
            LOG.trace("Export CSV button clicked");
            exportCSV_Anchor.getElement().executeJs("this.click()");
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout( downloadLogBtn, downloadLogAnchor, // anchor has to be added if the button clicks on the Anchor
                exportCSVbttn, exportCSV_Anchor  );
        buttonsLayout.setWidth("65%");
        buttonsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        HorizontalLayout mainHorizontalLayout = new HorizontalLayout(filterLayout , buttonsLayout );
        mainHorizontalLayout.setWidth("100%");

        return mainHorizontalLayout;
    }

    private String prepareCSV()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        String csv = "\nDownloaded " + dtf.format(now);
        csv = csv + "\n,CLSS ID,SIS ID,Term,Term Code,Department Code,Subject Code,Catalog Number,Course,Section #,Course Title,Section Type,Title/Topic,Meeting Pattern,Instructor,Room,Status,Session,Campus,Inst. Method,Integ. Partner,Schedule Print,Consent,Credit Hrs Min,Credit Hrs,Grade Mode,Attributes,Room Attributes,Enrollment,Maximum Enrollment,Prior Enrollment,Projected Enrollment,Wait Cap,Rm Cap Request,Cross-listings,Link To,Comments,Notes\n";
        List<Course> courses = service.getAllCourses();
        for (Course course : courses){
            csv = csv + course.toRow();
        }
        return csv;
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

}
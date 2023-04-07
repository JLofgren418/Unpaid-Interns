package edu.unomaha.pkischeduler.ui;


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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.service.CourseService;

/**
 * ExportView creates the UI for the Edit page.
 * The UI is designed to allow the user to export the data
 *  from the system after processing, editing and verifying.
 */
@Route(value = "export")
@PageTitle("Export")
public class ExportView extends AppLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    CourseService service;

    /**
     * ExportView calls the available methods to set up the UI.
     * @param service The service class that allows access to the
     *                room and course tables in the database.
     */
    public ExportView(CourseService service) {
        this.service = service;
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
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
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
        HorizontalLayout h1 = new HorizontalLayout(filterText);
        h1.setAlignItems(FlexComponent.Alignment.CENTER);
        h1.setWidth("35%");
        Button exportCSV = new Button("Export CSV",VaadinIcon.DOWNLOAD.create());
        exportCSV.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout h2 = new HorizontalLayout(exportCSV);
        h2.setAlignItems(FlexComponent.Alignment.CENTER);
        h2.setWidth("65%");
        h2.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        HorizontalLayout h3 = new HorizontalLayout(h1, h2);
        h3.setWidth("100%");
        h3.setHeight("4%");
        return h3;
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
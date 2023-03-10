package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.service.CourseService;

@Route(value = "export")
@PageTitle("Export")
public class ExportView extends VerticalLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    CourseService service;

    Button exportCSV = new Button("Export CSV");

    public ExportView(CourseService service) {
        this.service = service;
        addClassName("export-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
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

        Button redirect1 = new Button("Go to Import");
        redirect1.addClickListener(e ->
                redirect1.getUI().ifPresent(ui ->
                        ui.navigate("")));

        Button redirect2 = new Button("Go to Edit");
        redirect2.addClickListener(e ->
                redirect2.getUI().ifPresent(ui ->
                        ui.navigate("edit")));
        Button exportCSV = new Button("Export CSV");
        HorizontalLayout h1 = new HorizontalLayout(redirect1,redirect2,exportCSV);
        h1.setWidth("100%");
        h1.setJustifyContentMode(JustifyContentMode.CENTER);
        h1.addClassName("toolbar");
        return h1;
    }

    private void updateList() {
        grid.setItems(service.filterCourses(filterText.getValue()));
    }


}
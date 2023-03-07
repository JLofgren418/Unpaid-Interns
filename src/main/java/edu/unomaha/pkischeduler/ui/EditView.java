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

@Route(value = "edit")
@PageTitle("Edit")
public class EditView extends VerticalLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    EditForm form;
    CourseService service;


    public EditView(CourseService service) {
        this.service = service;
        addClassName("edit-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new EditForm(service.findAllRooms());
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title");
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days");
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time");
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Course Code");
        //grid.addColumn(room -> room.getRoom().getNumber()).setHeader("Room");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by course");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Course");

        Button redirect1 = new Button("Go to Import");
        redirect1.addClickListener(e ->
                redirect1.getUI().ifPresent(ui ->
                        ui.navigate("")));

        Button redirect2 = new Button("Go to Export");
        redirect2.addClickListener(e ->
                redirect2.getUI().ifPresent(ui ->
                        ui.navigate("export")));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton,redirect1,redirect2);
        toolbar.setWidth("100%");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.findAllCourses(filterText.getValue()));
    }
}
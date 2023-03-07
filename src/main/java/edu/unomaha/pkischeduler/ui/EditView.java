package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.service.CourseService;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "edit")
@PageTitle("Edit")
public class EditView extends VerticalLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    CourseService service;


    public EditView(CourseService service) {
        this.service = service;
        var crud = new GridCrud<>(Course.class, service);
        Grid<Course> grid = crud.getGrid();
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title");
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days");
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time");
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Instructor");
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room");
        setSizeFull();
        crud.setSizeFull();
        //crud.getCrudFormFactory().setVisibleProperties("courseCode", "courseTitle");
        add(getToolbar(), crud);
    }
    private HorizontalLayout getToolbar() {

        Button redirect1 = new Button("Go to Import");
        redirect1.addClickListener(e ->
                redirect1.getUI().ifPresent(ui ->
                        ui.navigate("")));

        Button redirect2 = new Button("Go to Export");
        redirect2.addClickListener(e ->
                redirect2.getUI().ifPresent(ui ->
                        ui.navigate("export")));

        HorizontalLayout toolbar = new HorizontalLayout(redirect1,redirect2);
        toolbar.setWidth("100%");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CourseService;

@Route(value = "edit")
@PageTitle("Edit")
public class EditView extends AppLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    CourseService service;
    Crud<Course>  crud;

    public EditView(CourseService service) {
        this.service = service;
        grid.setItems(service.getAllCourses());
        crud = new Crud<>(Course.class, grid, createEditor());
        crud.addSaveListener(click -> service.add(crud.getEditor().getItem()));
        crud.addSaveListener(click -> grid.setItems(service.getAllCourses()));
        crud.addDeleteListener(click -> service.delete(crud.getEditor().getItem()));
        crud.addDeleteListener(click -> grid.setItems(service.getAllCourses()));
        setupGrid();
        crud.setSizeFull();
        addToNavbar(getTabs());
        setContent(crud);

    }
    private HorizontalLayout getTabs() {

        Tab redirect1 = new Tab(VaadinIcon.UPLOAD.create());
        redirect1.add(new RouterLink("Upload", ImportView.class));

        Tab redirect2 = new Tab(VaadinIcon.DOWNLOAD.create());
        redirect2.add(new RouterLink("Export", ExportView.class));

        HorizontalLayout h1 = new HorizontalLayout(redirect1, redirect2);

        h1.addClassName("toolbar");
        h1.setWidth("33%");
        return h1;
    }

    private CrudEditor<Course> createEditor() {

        TextField courseTitle = new TextField("Course Title");
        TextField courseCode = new TextField("Course Code");
        TextField sectionType = new TextField("Section Type");
        TextField meetingDays = new TextField("Meeting Days");
        TextField meetingTime = new TextField("Meeting Time");
        TextField crossListings = new TextField("Cross Listings");
        NumberField expectedEnrollment = new NumberField("Max Enrollment");
        TextField sectionNumber = new TextField("Section Number");
        ComboBox<Room> room = new ComboBox<>("Room");
        ComboBox<Instructor> instructor = new ComboBox<>("Instructor");

        room.setItems(service.getAllRooms());
        instructor.setItems(service.getAllInstructors());

        FormLayout form = new FormLayout(courseTitle, courseCode, sectionType,
                meetingDays, meetingTime, crossListings, expectedEnrollment,
                sectionNumber, room, instructor);

        Binder<Course> binder = new Binder<>(Course.class);

        binder.forField(courseTitle).asRequired().bind(Course::getCourseTitle,
                Course::setCourseTitle);
        binder.forField(courseCode).asRequired().bind(Course::getCourseCode,
                Course::setCourseCode);
        binder.forField(sectionType).asRequired().bind(Course::getSectionType,
                Course::setSectionType);
        binder.forField(meetingDays).asRequired().bind(Course::getMeetingDays,
                Course::setMeetingDays);
        binder.forField(meetingTime).asRequired().bind(Course::getMeetingTime,
                Course::setMeetingTime);
        binder.forField(crossListings).asRequired().bind(Course::getCrossListings,
                Course::setCrossListings);
        binder.forField(expectedEnrollment).asRequired().bind(Course::getExpectedEnrollment,
                Course::setExpectedEnrollment);
        binder.forField(sectionNumber).asRequired().bind(Course::getSectionNumber,
                Course::setSectionNumber);
        binder.forField(room).asRequired().bind(Course::getRoom,
                Course::setRoom);
        binder.forField(instructor).asRequired().bind(Course::getInstructor,
                Course::setInstructor);
        return new BinderCrudEditor<>(binder, form);
    }

    private void setupGrid() {
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code");
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title");
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days");
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time");
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Instructor");
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room");
        Crud.addEditColumn(grid);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }


}
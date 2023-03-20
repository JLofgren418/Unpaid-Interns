package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
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
    TextField filterText = new TextField();
    private Span status;

    public EditView(CourseService service) {
        this.service = service;
        grid.setItems(service.getAllCourses());
        crud = new Crud<>(Course.class, grid, createEditorForm());
        setupGrid();
        crud.setSizeFull();
        addToNavbar(getTabs());
        setContent(getEditContent());
    }

    private Component getEditContent() {
        VerticalLayout content = new VerticalLayout(getToolbar(),crud);
        content.setFlexGrow(2, crud);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
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

    private HorizontalLayout getToolbar()
    {
        filterText.setPlaceholder("Filter by course code");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        HorizontalLayout h1 = new HorizontalLayout(filterText);
        h1.setAlignItems(FlexComponent.Alignment.CENTER);
        //h1.setHeight("4%");

        HorizontalLayout h3 = new HorizontalLayout(h1,getDeleteButton());
        h3.setHeight("4%");
        h3.setWidth("100%");

        return h3;
    }

    private HorizontalLayout getDeleteButton()
    {
        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete Courses?");
        dialog.setText("Are you sure you want to permanently delete all items?");

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> setStatus("Canceled"));

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(event -> setStatus("Deleted"));
        dialog.addConfirmListener(click -> service.deleteAll());
        dialog.addConfirmListener(click -> updateList());

        Button deleteAll = new Button("Delete All", VaadinIcon.TRASH.create());
        deleteAll.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        deleteAll.addClickListener(event -> {
            dialog.open();
            status.setVisible(false);
        });
        HorizontalLayout h2 = new HorizontalLayout(deleteAll);
        h2.setAlignItems(FlexComponent.Alignment.CENTER);
        h2.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        h2.setWidth("100%");

        return h2;
    }



    private CrudEditor<Course> createEditorForm() {

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
        crud.addSaveListener(click -> service.add(crud.getEditor().getItem()));
        crud.addSaveListener(click -> grid.setItems(service.getAllCourses()));
        crud.addDeleteListener(click -> service.delete(crud.getEditor().getItem()));
        crud.addDeleteListener(click -> grid.setItems(service.getAllCourses()));

    }


    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
    }


    private void updateList() {

        grid.setItems(service.filterCourses(filterText.getValue()));
    }


}
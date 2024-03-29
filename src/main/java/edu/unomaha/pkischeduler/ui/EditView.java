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
import edu.unomaha.pkischeduler.data.entity.CourseChange;
import edu.unomaha.pkischeduler.data.entity.Instructor;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CRIService;
import edu.unomaha.pkischeduler.data.service.CourseChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EditView creates the UI for the Edit page.
 * This UI is designed to allow users to perform
 *  CRUD operations on data that is imported
 *  into the system.
 */
@Route(value = "edit")
@PageTitle("Edit")
public class EditView extends AppLayout {
    private static final Logger LOG = LoggerFactory.getLogger(EditView.class);

    /**
     * Service provider which allows the application to access
     * the courseChangeRepository.
     */
    private CourseChangeService courseChangeService;

    /**  Used to track on-edit changes   */
    CourseChange courseChange = new CourseChange();

    /**
     * A grid component that contains courses.
     */
    Grid<Course> grid = new Grid<>(Course.class);

    /**
     * Service provider which allows the application to access
     * the repositories for Courses, Rooms, and Instructors (CRI)
     */
    CRIService service;

    /**
     * A component for performing CRUD operations courses
     */
    Crud<Course>  crud;

    /**
     * A text field component that allows the user to filter the grid.
     */
    TextField filterText = new TextField();

    /**
     * Allows the edit form to be hidden when not in use.
     */
    private Span status;

    /**
     * Edit View calls the necessary methods to create the UI.
     *
     * @param service A service class used to access the course, room,
     *                 and instructor tables in the database.
     * @param courseChangeService The service class used to access the course
     *                            change table in the database.
     */
    public EditView(CRIService service, CourseChangeService courseChangeService) {
        this.service = service;
        this.courseChangeService = courseChangeService;
        grid.setItems(service.getAllCourses());
        crud = new Crud<>(Course.class, grid, createEditorForm());
        setupGrid();
        crud.setSizeFull();
        addToNavbar(getTabs());
        setContent(getEditContent());
    }

    /**
     * This function compiles the toolbar and
     * the grid into a vertical layout which is then
     * added to the UI.
     * @return The content containing the grid and the toolbar
     *  in a vertical layout.
     */
    private Component getEditContent() {
        VerticalLayout content = new VerticalLayout(getToolbar(),crud);
        content.setFlexGrow(2, crud);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
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
     * This function provides a horizontal layout containing
     * the grid filter and the delete button.
     * This Layout is added below the navigation bar and
     * above the grid.
     * @return A horizontal layout containing both the
     * delete button and the grid filter
     */
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


    /**
     * This method provides a horizontal layout that contains
     * the delete button.
     * This button is set up to delete every item in both the room and
     * course tables.
     * Upon clicking, a dialogue box is displayed to confirm the (risky) action.
     * @return A horizontal layout that contains the delete button.
     */
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
        dialog.addConfirmListener(event -> onDeleteAll( ));
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


    /**
     * This function provides the functionality for performing CRUD
     *  operations on course data in the system.
     * A binder is used to link the form to the getters and
     *  setters inside the course classs to the fields in the grid.
     * @return A CrudEditor form that is used to perform
     * CRUD operations on data in the system.
     */
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

    /**
     * This function populates the grid with the necessary fields from
     *  items in the database.
     * Additionally, the method adds click listeners that allow the
     *  buttons of the form to execute database CRUD operations.
     */
    private void setupGrid() {
        grid.setColumns();
        grid.addColumn(Course::getCourseCode).setHeader("Course Code").setSortable(true);
        grid.addColumn(Course::getCourseTitle).setHeader("Course Title").setSortable(true);
        grid.addColumn(Course::getMeetingDays).setHeader("Meeting Days").setSortable(true);
        grid.addColumn(Course::getMeetingTime).setHeader("Meeting Time").setSortable(true);
        grid.addColumn(course -> course.getInstructor().getName()).setHeader("Instructor").setSortable(true);
        grid.addColumn(Course::getExpectedAsInt).setHeader("Maximum Enrollment").setSortable(true);
        grid.addColumn(course -> course.getRoom().getCapacity()).setHeader("Room Capacity").setSortable(true);
        grid.addColumn(course -> course.getRoom().getNumber()).setHeader("Room").setSortable(true);
        Crud.addEditColumn(grid);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setMultiSort(true);

        crud.addSaveListener(click -> service.add(crud.getEditor().getItem()));
        crud.addSaveListener(click -> grid.setItems(service.getAllCourses()));

        crud.addDeleteListener(click -> service.delete(crud.getEditor().getItem()));
        crud.addDeleteListener(click -> grid.setItems(service.getAllCourses()));

        {   // Listeners on edit or delete - for audit log purposes
            crud.addDeleteListener(event -> onDeleteEvent(event));  // on delete
            crud.addEditListener(event -> onEditButtonClick(event)); // before edit
            crud.addSaveListener(click -> onEditButtonSaveClicked()); // after edit
        }
    }
    private void onDeleteEvent(Crud.DeleteEvent<Course> event){
        LOG.debug("onDeleteEvent():");
        try {
            Course deletedCourse =  event.getItem().clone();
            courseChange.setDelete(deletedCourse);
            courseChangeService.saveCourseChange(courseChange);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


    }


    private void onDeleteAll() {
        LOG.debug("onDeleteAll():");
        service.getAllCourses().forEach(course -> {
            CourseChange deletedCourse = new CourseChange();
            deletedCourse.setDelete(course);
            courseChangeService.saveCourseChange(deletedCourse);
        });
    }



    /**
     * This method is called when the user clicks the edit button in the grid.
     * Before any data is edited
     */
    private void onEditButtonClick(  Crud.EditEvent<Course> event){
        courseChange = new CourseChange();
        Course courseBeforeEdit = event.getItem();
        try {
            courseChange.setBeforeCourseChange(  courseBeforeEdit.clone()  );
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method is called when the user clicks the save button in the editor.
     * After any data is edited
     */
    private void onEditButtonSaveClicked(){
        LOG.debug( "onEditButtonSaveClicked():");
        Course course = crud.getEditor().getItem();
        try {
            if (courseChange == null){
                courseChange = new CourseChange();
            }
            courseChange.setAfterCourseChange(  course.clone() );
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        LOG.debug( "\tchange -> " +    courseChange.toString() );
        courseChangeService.saveCourseChange(courseChange);
    }


    /**
     * This method allows the edit form to be hidden when not in use.
     * @param value a value indicating whether
     *              the edit form is displayed or not
     */
    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
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
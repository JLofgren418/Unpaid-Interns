package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Room;
import edu.unomaha.pkischeduler.data.service.CRIService;

import java.util.LinkedList;
import java.util.List;

/**
 * RoomView creates the UI for the Room x Room schedule view.
 * This UI is designed to allow the user to view the courses
 *  that are assigned to any given room.
 * Uses an accordion to display information.
 */
@Route(value = "room")
@PageTitle("Room")
public class RoomView extends AppLayout {
    /**
     * Service provider which allows the application to access
     * the repositories for Courses, Rooms, and Instructors (CRI)
     */
    CRIService service;

    /**
     * A list of all courses.
     */
    List<Course> courses;

    /**
     * A list of rooms.
     */
    List<Room> rooms;

    /**
     * An accordion component for the UI.
     */
    Accordion accordion = new Accordion();

    /**
     * A special HorizontalLayout component used for IU formatting
     * and to hold the label containing the page description.
     */
    HorizontalLayout spacer = new HorizontalLayout();

    /**
     * This function calls the necessary methods to create the UI.
     * @param service A service class used to access the course, room,
     *                 and instructor tables in the database.
     */
    public RoomView(CRIService service) {
        addClassName("room-view");
        this.service = service;
        rooms = service.getAllRooms();
        courses = service.getAllCourses();
        updateCourseList();
        configureAccordion();
        setContent(getRoomContent());
        addToNavbar(getTabs());
    }


    /**
     * This function compiles a short description of the page
     *  purpose and the accordion component into a vertical layout.
     * @return A vertical layout component containing the accordion
     *  and a label.
     */
    private Component getRoomContent() {
        spacer.setWidth("100");
        spacer.setHeight("4%");
        Label pageTitle = new Label("Schedules by Room");
        spacer.add(pageTitle);
        spacer.setAlignItems(FlexComponent.Alignment.BASELINE);
        accordion.close();
        VerticalLayout content = new VerticalLayout(spacer,accordion);
        content.setFlexGrow(2, accordion);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }


    /**
     * This function iterates through each room and each course to
     *  determine which courses are associated with a given room.
     * At each iteration the room object gets an updated list of
     *  courses that are assigned to it.
     * This function allows the accordion to display the courses
     *  that are assigned to any given room.
     */
    private void updateCourseList() {

        for (Room room : rooms) {
            List<Course> courseList = new LinkedList<>();

            for (Course course : courses) {
                if (course.getRoom().equals(room)) {
                    courseList.add(course);
                }
                room.setCourses(courseList);
            }
        }
    }


    /**
     * This function creates the accordion component and populates
     *  the component with the database objects.
     */
    private void configureAccordion()
    {
        for (Room room : rooms) {
            VerticalLayout courseLayout = new VerticalLayout();
            String roomNumber = room.toString();

            if (room.getCourses() != null && room.getCourses().size() > 0) {
                for (int j = 0; j < room.getCourses().size(); j++) {
                    Span course = new Span(room.getCourses().get(j).getDetails());
                    courseLayout.add(course);
                }
                AccordionPanel panel = accordion.add(" " + roomNumber, courseLayout);
                panel.addThemeVariants(DetailsVariant.FILLED);
            }
        }
        accordion.setSizeFull();
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
}

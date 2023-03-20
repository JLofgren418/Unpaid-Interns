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
import edu.unomaha.pkischeduler.data.service.CourseService;

import java.util.LinkedList;
import java.util.List;

@Route(value = "room")
@PageTitle("Room")
public class RoomView extends AppLayout {
    CourseService service;
    List<Course> courses;
    List<Room> rooms;
    Accordion accordion = new Accordion();
    HorizontalLayout spacer = new HorizontalLayout();
    public RoomView(CourseService service) {
        addClassName("room-view");
        this.service = service;
        rooms = service.getAllRooms();
        courses = service.getAllCourses();
        updateCourseList();
        configureAccordion();
        setContent(getRoomContent());
        addToNavbar(getTabs());
    }


    private Component getRoomContent() {
        spacer.setWidth("100");
        spacer.setHeight("4%");
        Label pageTitle = new Label("Schedules by Room");
        spacer.add(pageTitle);
        spacer.setAlignItems(FlexComponent.Alignment.BASELINE);
        VerticalLayout content = new VerticalLayout(spacer,accordion);
        content.setFlexGrow(2, accordion);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

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

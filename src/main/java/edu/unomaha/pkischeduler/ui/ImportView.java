package edu.unomaha.pkischeduler.ui;


import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import edu.unomaha.pkischeduler.data.entity.Course;
import edu.unomaha.pkischeduler.data.entity.Schedule;
import edu.unomaha.pkischeduler.data.service.CourseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.unomaha.pkischeduler.data.service.ScheduleService;


import java.util.Collections;
import java.util.stream.Stream;

@Route(value = "")
@PageTitle("Import")
public class ImportView extends VerticalLayout {
    Grid<Schedule> grid = new Grid<>(Schedule.class);
    TextField filterText = new TextField();
    ScheduleService service;

    public ImportView(ScheduleService service) {
        this.service = service;
        addClassName("import-view");
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
        grid.addClassNames("schedule-grid");
        grid.setSizeFull();
        grid.setColumns();//remove to check db item
        grid.addColumn(course -> course.getCourse().getCourseCode()).setHeader("Course Code");
        grid.addColumn(course -> course.getCourse().getCourseTitle()).setHeader("Course Title");
        grid.addColumn(course -> course.getCourse().getMeetingTime()).setHeader("Meeting time");
        grid.addColumn(course -> course.getCourse().getMeetingDays()).setHeader("Meeting Days");
        grid.addColumn(course -> course.getCourse().getInstructor().getName()).setHeader("Instructor");
        grid.addColumn(room -> room.getRoom().getNumber()).setHeader("Room");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by course");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        MultiFileMemoryBuffer multiFileMemoryBuffer = new MultiFileMemoryBuffer();
        Upload multiFileUpload = new Upload(multiFileMemoryBuffer);

        Button redirect1 = new Button("Go to edit");
        redirect1.addClickListener(e ->
                redirect1.getUI().ifPresent(ui ->
                        ui.navigate("edit")));

        Button redirect2 = new Button("Go to Export");
        redirect2.addClickListener(e ->
                redirect2.getUI().ifPresent(ui ->
                        ui.navigate("export")));

        Button process = new Button("Process Schedule");

        HorizontalLayout h1 = new HorizontalLayout(process,redirect1,redirect2,multiFileUpload);
        h1.setAlignItems(Alignment.BASELINE);

        HorizontalLayout toolbar = new HorizontalLayout(filterText,h1);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.setWidth("100%");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }

    private void updateList() {

       grid.setItems(service.findAllSchedules(filterText.getValue()));
    }
}
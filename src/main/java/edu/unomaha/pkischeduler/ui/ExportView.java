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

@Route(value = "export")
@PageTitle("Export")
public class ExportView extends AppLayout {
    Grid<Course> grid = new Grid<>(Course.class);
    TextField filterText = new TextField();
    CourseService service;

    public ExportView(CourseService service) {
        this.service = service;
        addClassName("export-view");
        configureGrid();
        setContent(getExportContent());
        addToNavbar(getTabs());
        updateList();
    }

    private Component getExportContent() {
        VerticalLayout content = new VerticalLayout(getToolbar(),grid);
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

    private HorizontalLayout getTabs() {
        filterText.setPlaceholder("Filter by course code");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Tab redirect1 = new Tab(VaadinIcon.UPLOAD.create());
        redirect1.add(new RouterLink("Upload", ImportView.class));
        redirect1.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        Tab redirect2 = new Tab(VaadinIcon.PENCIL.create());
        redirect2.add(new RouterLink("Edit", EditView.class));
        redirect2.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        HorizontalLayout h1 = new HorizontalLayout(redirect1,redirect2);
        h1.setWidth("20%");
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

    private void updateList() {
        grid.setItems(service.filterCourses(filterText.getValue()));
    }


}
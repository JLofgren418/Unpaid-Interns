package edu.unomaha.pkischeduler.ui;


import edu.unomaha.pkischeduler.data.entity.Room;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;


import java.util.List;

public class EditForm extends FormLayout {

    TextField courseCode = new TextField("Course Code");
    TextField courseTitle = new TextField("Course Name");
    TextField meetingDays = new TextField("Meeting Days");
    TextField meetingTime = new TextField("Meeting Time");
    TextField instructor = new TextField("Instructor");
    ComboBox<Room> room = new ComboBox<>("Room");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public EditForm(List<Room> rooms) {
        addClassName("edit-form");

        room.setItems(rooms);
        room.setItemLabelGenerator(Room::toString);

        add(courseCode,
                courseTitle,
                meetingDays,
                meetingTime,
                instructor,
                room,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}
package com.example.ui;

import com.example.beans.AddTaskMessageBean;
import com.example.task.FakeTaskService;
import com.example.task.domain.TaskModel;
import com.example.task.domain.TaskPriorityEnum;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    //    private VerticalLayout mainPage = new VerticalLayout();
    private VerticalLayout page = new VerticalLayout();


    private H1 title = new H1("ToDo");
    private Div taskContainer = new Div();

    private TextField newTaskField = new TextField();
    private ComboBox<TaskPriorityEnum> taskPriorityComboBox = new ComboBox<>("Priority");
    private Button addTaskButton = new Button("Add task");

    private Grid<TaskModel> taskGrid;

    private FakeTaskService fakeTaskService;

    @Autowired
    public MainView(AddTaskMessageBean bean, FakeTaskService fakeTaskService) {
        this.fakeTaskService = fakeTaskService;

        addEventListerToAddTaskButton(bean);
        setGridParams();

        elementsArrangement();
    }

    private void elementsArrangement() {
        page.add(title);
        page.setHorizontalComponentAlignment(Alignment.CENTER, title);

        taskContainerRendering();

        page.add(taskGrid);

        add(page);
    }

    private void taskContainerRendering() {
        HorizontalLayout line1 = new HorizontalLayout();
        HorizontalLayout line2 = new HorizontalLayout();

        setTaskPriorityComboBoxValues();

        line1.add(newTaskField);
        line1.setWidthFull();
        line2.add(taskPriorityComboBox, addTaskButton);

        taskContainer.setId("task-container");
        taskContainer.add(line1, line2);

        page.add(taskContainer);
        page.setHorizontalComponentAlignment(Alignment.CENTER, taskContainer);
    }

    private void setTaskPriorityComboBoxValues() {
        taskPriorityComboBox.setItems(new ArrayList<>(Arrays.asList(TaskPriorityEnum.values())));
    }

    private void setGridParams() {
        taskGrid = new Grid<>(TaskModel.class);

        taskGrid.setId("list");
        taskGrid.setColumns("message", "priority", "startTime", "endTime");
        taskGrid.setSortableColumns("message", "priority", "startTime", "endTime");
        addCheckboxColumnToGrid();

        refreshGrid();
    }

    private void addCheckboxColumnToGrid() {
        taskGrid.addComponentColumn(taskModel -> {
            Checkbox doneCheckbox = new Checkbox();
            doneCheckbox.setValue(taskModel.getDone());

            addEventListenerToTaskCheckbox(doneCheckbox, taskModel);

            return doneCheckbox;
        })
                .setHeader("Done")
                .setSortable(true);
    }

    private void refreshGrid() {
        taskGrid.setItems(getAllTasks());
    }

    private List<TaskModel> getAllTasks() {
        return fakeTaskService.findAllAsList();
    }

    private void addEventListerToAddTaskButton(AddTaskMessageBean bean) {
        addTaskButton.addClickListener(e -> {
            String message = newTaskField.getValue();
            Notification.show(bean.getMessage(message));
            fakeTaskService.addTask(
                    createTask(newTaskField.getValue(), taskPriorityComboBox.getValue()));
            newTaskField.clear();
            refreshGrid();
        });
    }

    private void addEventListenerToTaskCheckbox(Checkbox doneCheckbox, TaskModel taskModel) {
        doneCheckbox.addValueChangeListener(event -> {
            fakeTaskService.updateTaskModelIsDone(taskModel, event.getValue());
            refreshGrid();
        });
    }

    private TaskModel createTask(String taskMessage, TaskPriorityEnum priority) {
        return TaskModel.builder()
                .message(taskMessage)
                .priority(priority)
                .done(false)
                .startTime(DateTime.now())
                .build();
    }
}

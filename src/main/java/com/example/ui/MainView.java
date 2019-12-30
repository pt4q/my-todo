package com.example.ui;

import com.example.beans.MessageBean;
import com.example.task.FakeTaskService;
import com.example.task.domain.TaskModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.List;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    //    private VerticalLayout mainPage = new VerticalLayout();
    private VerticalLayout page = new VerticalLayout();
    private HorizontalLayout todosElements = new HorizontalLayout();

    private H1 title = new H1("ToDo list");
    private TextField newTaskField = new TextField();
    private Button addTaskButton = new Button("Add task");

    private Grid<TaskModel> taskGrid;
    private Checkbox done = new Checkbox();

    private FakeTaskService fakeTaskService;

    @Autowired
    public MainView(MessageBean bean, FakeTaskService fakeTaskService) {
        this.fakeTaskService = fakeTaskService;

        addEventListerToAddTaskButton(bean);
        setGridParams();

        elementsArrangement();
    }

    private void elementsArrangement() {
        page.add(title);
        page.setHorizontalComponentAlignment(Alignment.CENTER, title);

        todosElements.add(newTaskField, addTaskButton);
        page.add(todosElements);
        page.setHorizontalComponentAlignment(Alignment.CENTER, todosElements);

        page.add(taskGrid);

        add(page);
    }

    private void setGridParams(){
        taskGrid = new Grid<>(TaskModel.class);

        taskGrid.setId("list");
        taskGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        taskGrid.setHeightFull();

        taskGrid.setColumns("message", "modificationTime", "done");
       refreshGrid();
    }

    private void refreshGrid(){
        taskGrid.setItems(getAllTasks());
//        add(taskGrid);
    }

    private List<TaskModel> getAllTasks(){
        return fakeTaskService.findAllAsList();
    }

    private void addEventListerToAddTaskButton(MessageBean bean) {
        addTaskButton.addClickListener(e -> {
            Notification.show(bean.getMessage());

            fakeTaskService.addTask(
                    createTask(newTaskField.getValue())
            );
            newTaskField.clear();
            refreshGrid();
        });
    }

    private TaskModel createTask(String taskMessage) {
        return TaskModel.builder()
                .message(taskMessage)
                .done(false)
                .modificationTime(DateTime.now())
                .build();
    }
}

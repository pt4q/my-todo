package pl.com.ptaq.my_todo.task;

import pl.com.ptaq.my_todo.task.domain.TaskModel;
import pl.com.ptaq.my_todo.task.domain.TaskPriorityEnum;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FakeTaskService {

    private Map<Integer, TaskModel> tasks = new LinkedHashMap<>();

    {
        tasks.put(0, TaskModel.builder()
                .message("Task 1 <TEST>")
                .priority(TaskPriorityEnum.NORMAL)
                .done(false)
                .startTime(DateTime.now())
                .build());

        tasks.put(1, TaskModel.builder()
                .message("Task 2 <TEST>")
                .priority(TaskPriorityEnum.HIGH)
                .done(false)
                .startTime(DateTime.now())
                .build());

        tasks.put(2, TaskModel.builder()
                .message("Task 3 <TEST>")
                .priority(TaskPriorityEnum.LOW)
                .done(false)
                .startTime(DateTime.now())
                .build());
    }

    public boolean addTask(TaskModel taskModel) {
        if (taskModel == null)
            return false;

        return tasks.put(tasks.size(), taskModel) != null;
    }

    public Map<Integer, TaskModel> findAll() {
        return tasks;
    }

    public List<TaskModel> findAllAsList() {
        List<TaskModel> resultList = new ArrayList<>();
        tasks.forEach(resultList::add);

        return resultList;
    }

    public TaskModel updateTaskModelIsDone(TaskModel oldTask, Boolean done) {
        Integer taskId = findId(oldTask);
        oldTask.setDone(done);
        oldTask.setEndTime(DateTime.now());

        return tasks.put(taskId, oldTask);
    }

    public TaskModel updateTaskModelPriority(TaskModel oldTask, TaskPriorityEnum priority){
        Integer taskId = findId(oldTask);
        oldTask.setPriority(priority);

        return tasks.put(taskId, oldTask);
    }

    public Integer findId(TaskModel taskModelToFind) {
        for (Map.Entry<Integer, TaskModel> taskModelEntry : tasks.entrySet()) {
            Integer key = taskModelEntry.getKey();
            TaskModel taskModel = taskModelEntry.getValue();

            if (taskModel.equals(taskModelToFind))
                return key;
        }
        return null;
    }
}

package com.example.task;

import com.example.task.domain.TaskModel;
import com.helger.commons.collection.map.MapEntry;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FakeTaskService {

    private Map<Integer, TaskModel> tasks = new LinkedHashMap<>();

    {
        tasks.put(0, TaskModel.builder()
                .message("Task 1 <TEST>")
                .done(false)
                .modificationTime(DateTime.now())
                .build());

        tasks.put(1, TaskModel.builder()
                .message("Task 2 <TEST>")
                .done(false)
                .modificationTime(DateTime.now())
                .build());

        tasks.put(2, TaskModel.builder()
                .message("Task 3 <TEST>")
                .done(false)
                .modificationTime(DateTime.now())
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

    public TaskModel updateTaskModel(TaskModel oldTask, Boolean done) {
        Integer taskId = findId(oldTask);
        oldTask.setDone(done);
        oldTask.setModificationTime(DateTime.now());

        tasks.put(taskId, oldTask);
        return oldTask;
    }

    public Integer findId(TaskModel taskModelToFind) {
        for (Map.Entry<Integer, TaskModel> taskModelEntry : tasks.entrySet()) {
            Integer key = taskModelEntry.getKey();
            TaskModel taskModel = taskModelEntry.getValue();

            if (taskModelToFind.getMessage().equals(taskModel.getMessage()))
                if (taskModelToFind.getDone().equals(taskModel.getDone()))
                    if (taskModelToFind.getModificationTime().equals(taskModel.getModificationTime()))
                        return key;
        }
        return null;
    }
}

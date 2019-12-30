package com.example.task;

import com.example.task.domain.TaskModel;
import com.helger.commons.collection.map.MapEntry;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FakeTaskService {

    private Map<Integer, TaskModel> tasks = new LinkedHashMap<>();

    public boolean addTask(TaskModel taskModel){
        if (taskModel == null)
            return false;

        return tasks.put(tasks.size(), taskModel) != null;
    }

    public Map<Integer, TaskModel> findAll(){
        return tasks;
    }

    public List<TaskModel> findAllAsList(){
        List<TaskModel> resultList = new ArrayList<>();
        tasks.forEach(resultList::add);

        return resultList;
    }
}

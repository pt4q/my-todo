package com.example.beans;

import java.io.Serializable;
import java.time.LocalTime;

import com.example.task.domain.TaskModel;
import org.springframework.stereotype.Service;

@Service
public class AddTaskMessageBean implements Serializable {

    public String getMessage(String taskMessage) {
        return "Added:\n" + "\"" + taskMessage + "\"\n" + LocalTime.now();
    }
}

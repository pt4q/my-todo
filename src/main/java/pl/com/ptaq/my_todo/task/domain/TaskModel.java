package pl.com.ptaq.my_todo.task.domain;

import lombok.*;
import org.joda.time.DateTime;

@Builder
@Data
public class TaskModel {

    private String message;
    private DateTime startTime;
    private DateTime endTime;
    private TaskPriorityEnum priority;
    private Boolean done;
}

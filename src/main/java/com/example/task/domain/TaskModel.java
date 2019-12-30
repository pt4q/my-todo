package com.example.task.domain;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class TaskModel {

    private String message;
    private DateTime modificationTime;
    private Boolean done;
}

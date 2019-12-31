package com.example.task.domain;

public enum TaskPriorityEnum {

    HIGH("high"),
    NORMAL("normal"),
    LOW("low");

    private String priority;

    TaskPriorityEnum(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority;
    }
}

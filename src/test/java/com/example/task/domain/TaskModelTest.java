package com.example.task.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(JUnitParamsRunner.class)
public class TaskModelTest {

    private DateTime beginTime = DateTime.now();
    private DateTime stopTime = DateTime.now();

    private TaskModel firstTaskTestModel = TaskModel.builder()
            .message("Test message")
            .startTime(beginTime)
            .endTime(stopTime)
            .done(true)
            .build();

    private TaskModel secondTaskTestModel = TaskModel.builder()
            .message("Test message")
            .startTime(beginTime)
            .endTime(stopTime)
            .done(true)
            .build();

    private TaskModel notEqualTaskTestModel = TaskModel.builder()
            .message("Not equal test message")
            .build();

    @Parameters(method = "testModels")
    @Test()
    public void testEquals(TaskModel tm1, TaskModel tm2, boolean expected) {
        boolean isEqual = tm1.equals(tm2);
        assertThat(isEqual, is(expected));
    }

    @Parameters(method = "testModels")
    @Test
    public void testHashCode(TaskModel tm1, TaskModel tm2, boolean expected) {
        boolean isEqual = tm1.equals(tm2);
        assertThat(isEqual, is(expected));
    }

    private Object[] testModels() {
        return new Object[]{
                new Object[]{firstTaskTestModel, secondTaskTestModel, true},
                new Object[]{firstTaskTestModel, notEqualTaskTestModel, false},
                new Object[]{secondTaskTestModel, notEqualTaskTestModel, false}
        };
    }
}
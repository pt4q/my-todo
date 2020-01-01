package pl.com.ptaq.my_todo.beans;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class AddTaskMessageBean implements Serializable {

    public String getMessage(String taskMessage) {
        return "Added:\n" + "\"" + taskMessage + "\"\n" + LocalTime.now();
    }
}

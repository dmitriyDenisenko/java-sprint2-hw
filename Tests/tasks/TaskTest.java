package tasks;

import backend.tasks.Epic;
import backend.tasks.StatusTask;
import backend.tasks.Subtask;
import backend.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Formatter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {
    private Task task;
    @BeforeEach
    public void createTaskManager(){
        task = new Task("Name", "Descrip", 1, 60, "06.05.2022 05:00");
    }


    @Test
    public void shouldBeNormalEndTime(){
        assertEquals("06.05.2022 06:00", task.getEndTime());
    }
}
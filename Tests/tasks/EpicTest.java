package tasks;

import backend.tasks.Epic;
import backend.tasks.StatusTask;
import backend.tasks.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epic;

    @BeforeEach
    public void createTaskManager() {
        epic = new Epic("Name", "Descrip", 1);
    }


    @Test
    public void shouldBeEmptySubtusksMap() {
        Map<Integer, Subtask> subtasks = epic.getSubtasks();
        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void shouldBeNewEpicWhenAllSubtaskNew() {
        epic.setSubtask(new Subtask(epic, "NameSubtask1", "DesSubtask1", 1));
        epic.setSubtask(new Subtask(epic, "NameSubtask2", "DesSubtask2", 2));
        assertEquals(StatusTask.NEW, epic.getStatus());
    }

    @Test
    public void shouldBeDoneEpicWhenAllSubtaskDone() {
        Subtask firstSubtask = new Subtask(epic, "NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic, "NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.DONE, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressWhenSubtaskNewAndDone() {
        Subtask firstSubtask = new Subtask(epic, "NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.NEW);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic, "NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressEpicWhenAllSubtaskInProgress() {
        Subtask firstSubtask = new Subtask(epic, "NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.IN_PROGRESS);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic, "NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.IN_PROGRESS);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldBeNullWhenCheckEndTimeWithEmptySubtasks() {
        assertEquals(null, epic.getEndTime());
    }

    @Test
    public void shouldBeHave60MinutesDurationEpicWithOneSubtask() {
        epic.setSubtask(new Subtask(epic, "name1", "des1", 2, 60,
                "06.05.2022 05:00"));
        assertEquals("06.05.2022 06:00", epic.getEndTime().format(epic.getFormatter()));
    }

    @Test
    public void shouldBeHave60MinutesDurationEpicWithThreeSubtasksAndOneStartTime() {
        epic.setSubtask(new Subtask(epic, "name1", "des1", 2, 20,
                "06.05.2022 05:00"));
        epic.setSubtask(new Subtask(epic, "name2", "des2", 3, 20,
                "06.05.2022 05:00"));
        epic.setSubtask(new Subtask(epic, "name3", "des3", 4, 20,
                "06.05.2022 05:00"));
        assertEquals("06.05.2022 06:00", epic.getEndTime().format(epic.getFormatter()));
    }

    @Test
    public void shouldBeHaveMinimalStartTimeWithTreeSubtasks() {
        epic.setSubtask(new Subtask(epic, "name1", "des1", 2, 20,
                "07.05.2022 05:00"));
        epic.setSubtask(new Subtask(epic, "name2", "des2", 3, 20,
                "08.05.2022 05:00"));
        epic.setSubtask(new Subtask(epic, "name3", "des3", 4, 20,
                "06.05.2022 05:00"));
        assertEquals("06.05.2022 06:00", epic.getEndTime().format(epic.getFormatter()));
    }
}
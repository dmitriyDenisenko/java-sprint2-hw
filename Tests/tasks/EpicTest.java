package tasks;

import backend.managers.InMemoryTaskManager;
import backend.managers.Managers;
import backend.managers.TaskManager;
import backend.tasks.Epic;
import backend.tasks.StatusTask;
import backend.tasks.Subtask;
import backend.tasks.TypeTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epic;
    @BeforeEach
    public void createTaskManager(){
        epic = new Epic("Name", "Descrip", 1);
    }


    @Test
    public void shouldBeEmptySubtusksMap(){
        HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void shouldBeNewEpicWhenAllSubtaskNew(){
        epic.setSubtask(new Subtask(epic,"NameSubtask1", "DesSubtask1", 1));
        epic.setSubtask(new Subtask(epic,"NameSubtask2", "DesSubtask2", 2));
        assertEquals(StatusTask.NEW, epic.getStatus());
    }

    @Test
    public void shouldBeDoneEpicWhenAllSubtaskDone(){
        Subtask firstSubtask = new Subtask(epic,"NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic,"NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.DONE, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressWhenSubtaskNewAndDone(){
        Subtask firstSubtask = new Subtask(epic,"NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.NEW);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic,"NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.DONE);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressEpicWhenAllSubtaskInProgress(){
        Subtask firstSubtask = new Subtask(epic,"NameSubtask1", "DesSubtask1", 1);
        firstSubtask.setStatus(StatusTask.IN_PROGRESS);
        epic.setSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(epic,"NameSubtask2", "DesSubtask2", 2);
        secondSubtask.setStatus(StatusTask.IN_PROGRESS);
        epic.setSubtask(secondSubtask);
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus());
    }
}
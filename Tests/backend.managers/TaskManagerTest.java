package backend.managers;

import backend.tasks.Epic;
import backend.tasks.StatusTask;
import backend.tasks.Subtask;
import backend.tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    @Test
    public void shouldBeHaveSubtaskEpic() {
        Epic epic = new Epic("Name", "des", 1);
        Subtask subtask = new Subtask(epic, "nameSub", "desSub", 2);
        manager.addEpic(epic);
        manager.addSubtask(subtask, 1);
        Subtask forCheck = manager.getAllSubtasks().get(2);
        assertEquals(epic, forCheck.getMainEpic());
    }

    @Test
    public void shouldBeHaveEpicStatusInProgress() {
        Epic epic = new Epic("Name", "des", 1);
        Subtask newSubtask = new Subtask(epic, "nameSub", "desSub", 2);
        Subtask subtaskDone = new Subtask(epic, "nameDoneSub", "desDoneSub", 3);
        subtaskDone.setStatus(StatusTask.DONE);
        manager.addEpic(epic);
        manager.addSubtask(newSubtask, 1);
        manager.addSubtask(subtaskDone, 1);
        assertEquals(StatusTask.IN_PROGRESS, manager.getEpics().get(1).getStatus());
    }

    @Test
    public void shouldBeHave1Task() {
        manager.addTask(new Task("name", "des", 1));
        assertEquals(1, manager.getTasks().size());
    }

    @Test
    public void shouldBeEmptyTasks() {
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void shouldBeHave1Epic() {
        manager.addEpic(new Epic("name", "des", 1));
        assertEquals(1, manager.getEpics().size());
    }

    @Test
    public void shouldBeEmptyEpics() {
        assertEquals(0, manager.getEpics().size());
    }

    @Test
    public void shouldBe1Subtask() {
        Epic epic = new Epic("name", "des", 1);
        Subtask subtask = new Subtask(epic, "name", "des", 2);
        manager.addEpic(epic);
        manager.addSubtask(subtask, 1);
        assertEquals(1, manager.getAllSubtasks().size());
    }

    @Test
    public void shouldBeEmptySubtasks() {
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void shouldBe1SubtaskInAddedEpic() {
        Epic epic = new Epic("EpicName", "EpicDes", 1);
        Subtask subtask = new Subtask(epic, "SubName", "SubDes", 2);
        manager.addEpic(epic);
        manager.addSubtask(subtask, 1);
        assertEquals(1, manager.getSubtasksEpic(epic).size());
    }

    @Test
    public void shouldBeEmptyInEpicList() {
        Epic epic = new Epic("EpicName", "EpicDes", 1);
        manager.addEpic(epic);
        assertEquals(0, manager.getSubtasksEpic(epic).size());
    }

    @Test
    public void shouldBeEmptyWhenHaveAndRemoveAll() {
        manager.addTask(new Task("Name", "Des", 1));
        manager.removeAll();
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void shouldBeEmptyWhenRemoveEmptyManager() {
        manager.removeAll();
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void shouldBeNormalWhenManagerHaveThisTask() {
        Task task = new Task("Name", "des", 1);
        manager.addTask(task);
        assertEquals(task, manager.getAllTypeTaskById(1));
    }

    @Test
    public void shouldBeNullWhenGetNumberNotHaveManager() {
        assertEquals(null, manager.getAllTypeTaskById(14));
    }

    @Test
    public void shouldBeNormalRemoving() {
        Task task = new Task("Name", "Des", 1);
        manager.addTask(task);
        manager.removeByIndex(1);
        assertEquals(null, manager.getAllTypeTaskById(1));
    }

    @Test
    public void shouldBeErrorWhenRemoveIncorrectId() {
        Error ex = assertThrows(Error.class, () -> manager.removeByIndex(14));
        assertEquals("Такой задачи нет", ex.getMessage());
    }

    @Test
    public void shouldBeErrorWhenAddOverlappingTasks() {
        manager.addTask(new Task("Name1", "des2", 1, 60, "09.05.2022 09:00"));
        Error ex = assertThrows(Error.class, () -> manager.addTask(new Task("name2", "des2", 2, 60, "09.05.2022 09:00")));
        assertEquals("Error! Runtimes overlap", ex.getMessage());
    }

}


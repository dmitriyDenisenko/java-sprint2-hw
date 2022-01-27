package backend;

import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.util.HashMap;

public interface TaskManager {
    public HashMap<Integer, Task> getTasks();

    public HashMap<Integer, Epic> getEpics();

    public HashMap<Integer, Subtask> getAllSubtasks();

    public HashMap<Integer, Subtask> getSubtasksEpic(Epic epic);

    public void removeAll();

    public Task getAllTypeTaskById(int id);

    public void addTask(Task task);

    public void addEpic(Epic epic);

    public void addSubtask(Subtask subtask, int idEpic);

    public void updateTask(Task task, int id);

    public void updateEpic(Epic epic, int id);

    public void updateSubtask(Subtask subtask, int id);

    public void removeByIndex(int id);
}

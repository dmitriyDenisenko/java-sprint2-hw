package backend.managers;

import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface TaskManager {

    boolean isValid(Task task);

    TreeSet<Task> getPrioritizedTasks();

    List<Task> history();

    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, Subtask> getAllSubtasks();

    Map<Integer, Subtask> getSubtasksEpic(Epic epic);

    void removeAll();

    Task getAllTypeTaskById(int id);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask, int idEpic);

    void updateTask(Task task, int id);

    void updateEpic(Epic epic, int id);

    void updateSubtask(Subtask subtask, int id);

    void removeByIndex(int id);
}

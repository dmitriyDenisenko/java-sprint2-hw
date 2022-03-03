package backend.managers;

import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager allHistory = Managers.getDefaultHistory();

    @Override
    public List<Task> history() {
        final List<Task> historyChecker = allHistory.getHistory();
        return historyChecker;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getAllSubtasks() {
        final HashMap<Integer, Subtask> allSubtasks = new HashMap<>();
        for (Epic epic : epics.values()) { //all epics
            for (Subtask subtask : epic.getSubtasks().values()) { //all subtasks in epic
                allSubtasks.put(subtask.getIndex(), subtask);
            }
        }
        return allSubtasks;
    }

    @Override
    public Map<Integer, Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epics.clear();
    }

    @Override
    public Task getAllTypeTaskById(int id) {
        final Map<Integer, Subtask> allSubtasks = getAllSubtasks();
        if (tasks.containsKey(id)) {
            final Task task = tasks.get(id);
            allHistory.add(task);
            return task;
        } else if (epics.containsKey(id)) {
            final Epic epic = epics.get(id);
            allHistory.add(epic);
            return epic;
        } else if (allSubtasks.containsKey(id)) {
            final Subtask subtask = allSubtasks.get(id);
            allHistory.add(subtask);
            return subtask;
        } else {
            System.out.println("Такой задачи нет");
            return null;
        }
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getIndex(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getIndex(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask, int idEpic) {
        if (epics.containsKey(idEpic)) {
            Epic epic = epics.get(idEpic);
            epic.setSubtask(subtask);
        } else {
            System.out.println("Error. Подзадача не существует.");
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            System.out.println("Error. Задачи не существует.");
        }

    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            Epic oldEpic = epics.get(id);
            epic.setSubtasks(oldEpic.getSubtasks()); //new epic now knows its list of subtasks
            updateMainEpicForSubtasks(epic); //Subtasks now know the new epic
            epics.put(id, epic);
        } else {
            System.out.println("Error. Такого эпика нет");
        }

    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        final Epic epic = findEpicSubtask(id);
        if (epic != null) {
            HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
            Subtask oldSubtask = subtasks.get(id); // old Subtask have info about mainEpic
            subtask.setMainEpic(oldSubtask.getMainEpic()); //now new subtask have info about mainEpic
            subtasks.put(id, subtask); //replace old subtask
        }
    }

    @Override
    public void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            allHistory.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
            if (!subtasks.isEmpty()) {
                for (Subtask subtask : subtasks.values()) {
                    allHistory.remove(subtask.getIndex());
                }
            }
            epics.remove(id);
            allHistory.remove(id);
        } else if (getAllSubtasks().containsKey(id)) {
            final Epic epic = findEpicSubtask(id);
            final HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(id);
            allHistory.remove(id);
            epic.setSubtasks(subtasks);
            epics.put(epic.getIndex(), epic);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    private void updateMainEpicForSubtasks(Epic epic) {
        for (Subtask subtask : epic.getSubtasks().values()) {
            subtask.setMainEpic(epic);
        }
    }

    private Epic findEpicSubtask(int id) {
        for (Epic epic : epics.values()) {
            if (epic.getSubtasks().containsKey(id)) {
                return epic;
            }
        }
        System.out.println("Error! Такой подзадачи нет.");
        return null;
    }

}

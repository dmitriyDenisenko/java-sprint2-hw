package Backend;

import Backend.AllTypesTasks.Epic;
import Backend.AllTypesTasks.Subtask;
import Backend.AllTypesTasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, Epic> epics = new HashMap<>();
    private static HashMap<Integer, Subtask> Allsubtasks = new HashMap<>();
    static Scanner input = new Scanner(System.in);

    public static HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public static HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getAllsubtasks() {
        return Allsubtasks;
    }

    public static ArrayList<Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public static Task getAllTasksTypesById(int id) {
        return tasks.get(id);
    }

    public static void addAllTaskTypes(Task task) {
        tasks.put(task.getIndex(), task);
    }

    public static void addAllTaskTypes(Epic epic) {
        epics.put(epic.getIndex(), epic);
    }

    public static void addAllTaskTypes(Subtask subtask) {
        Allsubtasks.put(subtask.getIndex(), subtask);
    }

    public static void updateAllTypeTask(Task task) {
        int id = task.getIndex();
        tasks.put(id, task);
    }

    public static void updateAllTypeTask(Epic epic) {
        int id = epic.getIndex();
        epics.put(id, epic);
    }

    public static void updateAllTypeTask(Subtask subtask) {
        int id = subtask.getIndex();
        tasks.put(id, subtask);
    }

    public static void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (int i = 0; i < subtasks.size(); i++) {
                Allsubtasks.remove(subtasks.get(i).getIndex());
            }
            subtasks.clear();
            epic.setSubtasks(subtasks);
            epics.remove(id);
        } else if (Allsubtasks.containsKey(id)) {
            Subtask subtask = Allsubtasks.get(id);
            Epic epic = subtask.getMainEpic();
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(id);
            epic.setSubtasks(subtasks);
            Allsubtasks.remove(id);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public static void removeAll() {
        tasks.clear();
        epics.clear();
        Allsubtasks.clear();
    }
}

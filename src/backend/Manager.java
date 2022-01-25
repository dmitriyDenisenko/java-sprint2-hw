package backend;

import backend.alltypestasks.Epic;
import backend.alltypestasks.Subtask;
import backend.alltypestasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> allSubtasks = new HashMap<>();
    private int serialNumber = 0;

    public void addTask(Task task) {
        tasks.put(task.getIndex(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getIndex(), epic);
    }

    public void addSubtask(Subtask subtask, int idEpic) {
        if(epics.containsKey(idEpic)){
            allSubtasks.put(subtask.getIndex(), subtask);
            Epic epic = subtask.getMainEpic();
            epic.setSubtask(subtask);
            epics.put(epic.getIndex(), epic);
        } else{
            System.out.println("Error. Подзадача не существует.");
        }
    }

    public void updateTask(Task task, int id) {
        if(tasks.containsKey(id)){
            tasks.put(id, task);
        }else{
            System.out.println("Error. Задачи не существует.");
        }

    }

    public void updateEpic(Epic epic, int id) {
        if(epics.containsKey(id)){
            Epic oldEpic = epics.get(id);
            epic.setSubtasks(oldEpic.getSubtasks()); //new epic now knows its list of subtasks
            updateMainEpicForSubtasks(epic); //Subtasks now know the new epic
            epics.put(id, epic);
        }else{
            System.out.println("Error. Такого эпика нет");
        }

    }

    public void updateSubtask(Subtask subtask, int id) {
        if(allSubtasks.containsKey(id)){
            updateSubtaskInMainEpic(subtask,id); //The old subtask is removed and information is added to its epic instead
            tasks.put(id, subtask);
        } else {
            System.out.println("Error. Такой подзадачи не существует");
        }
    }

    private void updateMainEpicForSubtasks(Epic epic){
        for(Subtask subtask: epic.getSubtasks()){
            subtask.setMainEpic(epic);
        }
    }

    private void updateSubtaskInMainEpic(Subtask subtask, int id){
        Epic epic = subtask.getMainEpic();
        ArrayList<Subtask> subtasksInMainEpic = epic.getSubtasks();
        subtasksInMainEpic.remove(allSubtasks.get(id));
        subtasksInMainEpic.add(subtask);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return allSubtasks;
    }

    public ArrayList<Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public int getForAddSerialNumber() {
        return serialNumber++;
    }


    public void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (int i = 0; i < subtasks.size(); i++) {
                allSubtasks.remove(subtasks.get(i).getIndex());
            }
            subtasks.clear();
            epics.remove(id);
        } else if (allSubtasks.containsKey(id)) {
            Subtask subtask = allSubtasks.get(id);
            Epic epic = subtask.getMainEpic();
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(subtask);
            epic.setSubtasks(subtasks);
            allSubtasks.remove(id);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void removeAll() {
        tasks.clear();
        epics.clear();
        allSubtasks.clear();
    }
}

package backend;

import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager{
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();


    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> getAllSubtasks() {
        HashMap<Integer, Subtask> allSubtasks = new HashMap<>();
        for(Epic epic: epics.values()){ //all epics
            for(Subtask subtask: epic.getSubtasks().values()){ //all subtasks in epic
                allSubtasks.put(subtask.getIndex(), subtask);
            }
        }
        return allSubtasks;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epics.clear();
    }

    @Override
    public Task getAllTypeTaskById(int id){
        HashMap<Integer, Subtask> allSubtasks = getAllSubtasks();
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            return task;
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            return epic;
        } else if (allSubtasks.containsKey(id)) {
            Subtask subtask = allSubtasks.get(id);
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
        if(epics.containsKey(idEpic)){
            Epic epic = epics.get(idEpic);
            epic.setSubtask(subtask);
        } else{
            System.out.println("Error. Подзадача не существует.");
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if(tasks.containsKey(id)){
            tasks.put(id, task);
        }else{
            System.out.println("Error. Задачи не существует.");
        }

    }

    @Override
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

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        Epic epic = findEpicSubtask(id);
        if(epic != null){
            HashMap<Integer,Subtask> subtasks = epic.getSubtasks();
            Subtask oldSubtask = subtasks.get(id); // old Subtask have info about mainEpic
            subtask.setMainEpic(oldSubtask.getMainEpic()); //now new subtask have info about mainEpic
            subtasks.put(id, subtask); //replace old subtask
        }
    }

    @Override
    public void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            epics.remove(id);
        } else if (getAllSubtasks().containsKey(id)) {
            Epic epic = findEpicSubtask(id);
            HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(id);
            epic.setSubtasks(subtasks);
            epics.put(epic.getIndex(), epic);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    private void updateMainEpicForSubtasks(Epic epic){
        for(Subtask subtask: epic.getSubtasks().values()){
            subtask.setMainEpic(epic);
        }
    }

    private Epic findEpicSubtask(int id){
        for(Epic epic: epics.values()){
            if(epic.getSubtasks().containsKey(id)){
                return epic;
            }
        }
        System.out.println("Error! Такой подзадачи нет.");
        return null;
    }

}

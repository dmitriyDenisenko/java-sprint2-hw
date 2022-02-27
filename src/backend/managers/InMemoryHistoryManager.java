package backend.managers;

import backend.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private MemoryLinkedList<Task> history = new MemoryLinkedList<>();
    private HashMap<Integer, Task> historyToEdit = new HashMap<>();

    private void removeNode(Task task){
        history.remove(task);
        historyToEdit.remove(task.getIndex());
    }

    @Override
    public void add(Task task) {
        if(historyToEdit.containsKey(task.getIndex())){
            removeNode(task);
        }
        if (history.size() < 10) {
            history.addLast(task);
        } else {
            history.remove(0);
            history.addLast(task);
        }
        historyToEdit.put(task.getIndex(), task);
    }


    @Override
    public List<Task> getHistory() {
        return history.getTasks(history);
    }

    @Override
    public void remove(int id){
        
    }
}
class MemoryLinkedList<Task> extends LinkedList<Task>{
    public MemoryLinkedList<Task> linkLast(MemoryLinkedList<Task> tasks, Task last){
        tasks.addLast(last);
        return tasks;
    }

    public ArrayList<Task> getTasks(MemoryLinkedList<Task> tasks){
        ArrayList<Task> tasksForManager = new ArrayList<>();
        tasksForManager.addAll(tasks);
        return tasksForManager;
    }
}

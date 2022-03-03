package backend.managers;

import backend.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private MemoryLinkedList history = new MemoryLinkedList();

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }


    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void remove(int id) {
        history.removeNode(id);
    }

}


class MemoryLinkedList {
    private Node head;
    private Node tail;
    private final HashMap<Integer, Node> historyToEdit = new HashMap<>();
    private int size = 0;


    public int getSize() {
        return size;
    }

    public void linkLast(Task element) {
        if (historyToEdit.containsKey(element.getIndex())) {
            removeNode(element.getIndex());
        }
        final Node oldTail = tail;
        final Node newNode = new Node(element, null, oldTail);
        historyToEdit.put(element.getIndex(), newNode);
        tail = newNode;
        if (oldTail == null) head = newNode;
        else oldTail.next = newNode;

        size++;

    }

    public ArrayList<Task> getTasks() {
        final ArrayList<Task> tasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            tasks.add(node.data);
            node = node.next;
        }
        return tasks;
    }

    public void removeNode(int idNode) {
        Node element = historyToEdit.get(idNode);
        final Node prev = element.prev;
        final Node next = element.next;
        if (prev == null && next != null) {
            next.prev = null;
            head = next;
        } else if (next == null && prev != null) {
            prev.next = null;
            tail = prev;
        } else {
            if (prev != null && next != null) {
                prev.next = next;
                next.prev = prev;
            }
        }
        size--;
    }

}

class Node {
    public Task data;
    public Node next;
    public Node prev;

    public Node(Task data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(data, node.data) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, next, prev);
    }
}


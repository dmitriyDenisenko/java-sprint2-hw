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
        final Node newNode = new Node(element, oldTail, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.setPrev(newNode);
        }
        historyToEdit.put(element.getIndex(), newNode);

        size++;

    }

    public ArrayList<Task> getTasks() {
        final ArrayList<Task> tasks = new ArrayList<>(size);
        Node node = head;
        while (node != null) {
            tasks.add(node.getData());
            node = node.getNext();
        }
        return tasks;
    }

    public void removeNode(int idNode) {
        if (historyToEdit.containsKey(idNode)) {
            Node element = historyToEdit.get(idNode);
            final Node prev = element.getPrev();
            final Node next = element.getNext();
            if (prev == null && next != null) {
                next.setPrev(null);
                head = next;
            } else if (next == null && prev != null) {
                prev.setNext(null);
                tail = prev;
            } else {
                if (prev != null && next != null) {
                    prev.setNext(next);
                    next.setPrev(prev);
                }
            }
            size--;
        }
    }

}

class Node {
    private Task data;
    private Node next;
    private Node prev;

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

    public Task getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}

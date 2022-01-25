package backend.alltypestasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();

    public Epic(String name, String description, int index) {
        super(name, description, index);
        this.setStatus(generateStatusEpic());
    }

    public void setSubtask(Subtask subtask){
        subtasks.put(subtask.getIndex(), subtask);
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    private String generateStatusEpic() {
        if (subtasks.isEmpty()) {
            return "NEW";
        }
        int countNew = 0;
        int countDone = 0;
        int countInProgress = 0;
        for (Subtask subtask : subtasks.values()) {
            String status = subtask.getStatus();
            if (status.equals("NEW")) {
                countNew++;
            } else if (status.equals("DONE")) {
                countDone++;
            } else if (status.equals("IN_PROGRESS")) {
                countInProgress++;
            }
        }
        if (countDone != 0 && countNew == 0 && countInProgress == 0) {
            return "DONE";
        } else if (countNew != 0 && countDone == 0 && countInProgress == 0) {
            return "NEW";
        } else {
            return "IN_PROGRESS";
        }
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String getStatus() {
        return generateStatusEpic();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks, this.getName(), this.getDescription());
    }

}

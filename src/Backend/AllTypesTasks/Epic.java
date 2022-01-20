package Backend.AllTypesTasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.setStatus(generateStatusEpic());
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

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    private String generateStatusEpic() {
        if (subtasks.isEmpty()) {
            return "NEW";
        }
        int countNew = 0;
        int countDone = 0;
        int countInProgress = 0;
        for (Subtask subtask : subtasks) {
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

    @Override
    public String getStatus() {
        return generateStatusEpic();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

}

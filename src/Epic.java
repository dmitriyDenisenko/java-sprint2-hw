import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic() {
        status = generateStatusEpic();
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
        return Objects.hash(super.hashCode(), subtasks, name, description);
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
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask subtask = subtasks.get(i);
            String status = subtask.getStatus();
            if (status == "NEW" && countDone == 0 && countInProgress == 0) {
                countNew++;
            } else if (status == "DONE" && countNew == 0 && countInProgress == 0) {
                countDone++;
            } else if (status == "IN_PROGRESS" && countNew == 0 && countDone == 0) {
                countInProgress++;
            }
            if ((countDone != 0 && (countNew != 0 || countInProgress != 0))
                    || (countNew != 0 && (countDone != 0 || countInProgress != 0))
                    || (countInProgress != 0 && (countNew != 0 && countDone != 0))) {
                return "IN_PROGRESS";
            }
        }
        if (countNew != 0) {
            return "NEW";
        } else if (countDone != 0) {
            return "DONE";
        }
        return "IN_PROGRESS";
    }

    @Override
    public String getStatus() {
        return generateStatusEpic();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

}

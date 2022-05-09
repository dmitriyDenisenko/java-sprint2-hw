package backend.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    private Map<Integer, Subtask> subtasks = new LinkedHashMap<>();

    public Epic(String name, String description, int index) {
        super(name, description, index);
        super.setType(TypeTask.EPIC);
        this.setStatus(generateStatusEpic());
    }

    public void setSubtask(Subtask subtask) {
        subtasks.put(subtask.getIndex(), subtask);
    }

    public void setSubtasks(Map<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    private StatusTask generateStatusEpic() {
        if (subtasks.isEmpty()) {
            return StatusTask.NEW;
        }
        int countNew = 0;
        int countDone = 0;
        int countInProgress = 0;
        for (Subtask subtask : subtasks.values()) {
            StatusTask status = subtask.getStatus();
            if (status.equals(StatusTask.NEW)) {
                countNew++;
            } else if (status.equals(StatusTask.DONE)) {
                countDone++;
            } else if (status.equals(StatusTask.IN_PROGRESS)) {
                countInProgress++;
            }
        }
        if (countDone != 0 && countNew == 0 && countInProgress == 0) {
            return StatusTask.DONE;
        } else if (countNew != 0 && countDone == 0 && countInProgress == 0) {
            return StatusTask.NEW;
        } else {
            return StatusTask.IN_PROGRESS;
        }
    }

    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (!subtasks.isEmpty()) {
            super.setDuration(Duration.ofMinutes(0));
            super.setStartTime(subtasks.entrySet().iterator().next().getValue().getStartTime());
            for (Subtask subtask : subtasks.values()) {
                if (super.getStartTime().isAfter(subtask.getStartTime())) {
                    super.setStartTime(subtask.getStartTime());
                }
                super.setDuration(super.getDuration().plus(subtask.getDuration()));
            }
            return super.getStartTime().plus(super.getDuration());
        } else {
            return null;
        }
    }

    @Override
    public Duration getDuration() {
        getEndTime();
        return super.getDuration();
    }

    @Override
    public LocalDateTime getStartTime(LocalDateTime startTime) {
        getEndTime();
        return super.getStartTime();
    }

    @Override
    public StatusTask getStatus() {
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

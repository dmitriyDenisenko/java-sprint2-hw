import java.util.Objects;

public class Subtask extends Task {
    Epic mainEpic;

    public Subtask(Epic epic) {
        this.mainEpic = epic;
        status = "NEW";
        epic.subtasks.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(mainEpic, subtask.mainEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }


}

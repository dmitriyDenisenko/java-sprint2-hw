package backend.tasks;

import java.util.Objects;

public class Subtask extends Task {
    private Epic mainEpic;

    public Subtask(Epic epic, String name, String description, int index) {
        super(name, description, index);
        this.mainEpic = epic;
        super.setType(TypeTask.SUBTASK);
    }

    public Subtask(Epic epic, String name, String description, int index, long durationInMinutes, String starTime) {
        super(name, description, index, durationInMinutes, starTime);
        this.mainEpic = epic;
        super.setType(TypeTask.SUBTASK);
    }

    public void setMainEpic(Epic mainEpic) {
        this.mainEpic = mainEpic;
    }

    public Epic getMainEpic() {
        return mainEpic;
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
        return Objects.hash(super.hashCode(), this.getName());
    }

    @Override
    public String toString() {
        return super.getIndex() + "," + super.getType() + "," + super.getName() + "," + super.getStatus() + "," + super.getDescription() + "," + mainEpic.getIndex();
    }

}

package backend.tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int mainEpicId;

    public Subtask(int epic, String name, String description, int index) {
        super(name, description, index);
        this.mainEpicId = epic;
        super.setType(TypeTask.SUBTASK);
    }

    public Subtask(int epic, String name, String description, int index, long durationInMinutes, String starTime) {
        super(name, description, index, durationInMinutes, starTime);
        this.mainEpicId = epic;
        super.setType(TypeTask.SUBTASK);
    }

    public void setMainEpicId(int mainEpic) {
        this.mainEpicId = mainEpic;
    }

    public int getMainEpic() {
        return mainEpicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(mainEpicId, subtask.mainEpicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getName());
    }

    @Override
    public String toString() {
        return super.getIndex() + "," + super.getType() + "," + super.getName() + "," + super.getStatus() + "," + super.getDescription() + "," + mainEpicId;
    }

}

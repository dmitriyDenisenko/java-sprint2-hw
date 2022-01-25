package backend.alltypestasks;

import java.util.Objects;

public class Subtask extends Task {
    private Epic mainEpic;

    public Subtask(Epic epic, String name, String description, int index) {
        super(name, description, index);
        this.mainEpic = epic;
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


}

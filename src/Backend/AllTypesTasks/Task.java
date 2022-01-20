package Backend.AllTypesTasks;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private String status = "NEW";
    private int index;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = "NEW";
        index = hashCode();
    }

    public int getIndex() {
        return index;
    }

    public int generateIndex() {
        index = hashCode();
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }
}

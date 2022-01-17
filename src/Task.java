import java.util.Objects;
import java.util.Scanner;

public class Task {
    Scanner input = new Scanner(System.in);
    protected String name;
    protected String description;
    protected String status = "NEW";
    protected int uniqueNumber;
    protected int index;

    public int generateIndex() {
        index = hashCode();
        return index;
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

    public void inputSetName() {
        this.name = input.nextLine();
    }

    public void inputSetDescription() {
        this.description = input.nextLine();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return uniqueNumber == task.uniqueNumber && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, uniqueNumber);
    }
}

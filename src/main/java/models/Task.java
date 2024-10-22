package models;
import java.time.LocalDate;

public class Task {
    private static int taskAmount = 0;

    private int taskID;
    private String title;
    private String description;
    private int isDone;
    private int repeats;
    private LocalDate dueDate;
    private int repeatDays;

    public Task(String title, String description, LocalDate dueDate, int isDone, int repeats, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.repeats = repeats;
        this.repeatDays = repeats != 0 ? repeatDays : 0;
    }


    public Task(int id, String title, String description, LocalDate dueDate, int isDone, int repeats, int repeatDays) {
        this.taskID = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.repeats = repeats;
        this.repeatDays = repeats != 0 ? repeatDays : 0;
    }


    public int getId() {return taskID;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getIsDone() {return isDone;}
    public int getRepeats() {return repeats;}
    public LocalDate getDueDate() {return dueDate;}

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setIsDone(int isDone) { this.isDone = isDone; }
    public void setRepeats(int repeats) {
        this.repeats = repeats;
        this.repeatDays = repeats != 0 ? repeatDays : 0;
    }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }


    private void updateDueDate() {
        if (this.repeats != 0 && this.repeatDays > 0) {
            this.dueDate = this.dueDate.plusDays(repeatDays);
            this.isDone = 0;
        }
    }


    public void toggleDone() {
        if (this.isDone != 0) {
            this.isDone = 0;
        } else {
            this.isDone = 1;
        }
    }


    @Override
    public String toString() {
        return "Gjoeremaal\n" +
                "ID: " + taskID + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Is Done: " + (isDone != 0 ? "Yes" : "No") + "\n" +
                "Repeats: " + (repeats != 0 ? "Yes, every " + repeatDays + " days" : "No") + "\n" +
                "Due Date: " + dueDate + "\n";
    }
}

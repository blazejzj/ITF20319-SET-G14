import java.util.ArrayList;

public class Project {

    // Instance variables
    private static int id = 0; // amount of projects
    private int projectID;
    private String title;
    private String description;
    private ArrayList<Task> tasks;

    // Constructor
    public Project(String title, String description) {
        this.title = title;
        this.description = description;
        this.tasks = new ArrayList<>();
        this.projectID = id++; // Each project has a unique id starting from 0
    }

    // Getters
    public ArrayList<Task> getTasks() {return tasks;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getId() {return projectID;}

    // Setters
    public void setDescription(String description) {this.description = description;}
    public void setTitle(String title) {this.title = title;}
    public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}

    // Methods
    public void addTask(Task task) {this.tasks.add(task);}
    public void removeTask(Task task) {this.tasks.remove(task);}

}

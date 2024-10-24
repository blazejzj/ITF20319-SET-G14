import java.util.ArrayList;

public class Project {

    // Variables
    private static int projectCounter = 0;
    private String title;
    private String description;
    private ArrayList<Task> tasks;
    private int userID;


    // Constructor 1
    public Project(String title, String description, ArrayList<Task> tasks, int userID) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.userID = userID;
    }

    // Constructor 2
    public Project(String title, String description, int userID) {
        this.title = title;
        this.description = description;
        this.userID = userID;
    }


    // Getters
    public String getTitle(){ return title;}
    public String getDescription(){ return description;}
    public ArrayList<Task> getTasks(){ return tasks;}
    public int getUserID(){ return userID;}


    // Setters
    public void setTitle(String Title) {this.title = Title;}
    public void setDescription(String Description) {this.description = Description;}
    public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}
    public void setUserID(int userID) {this.userID = userID;}


    // Methods
    public void addTask(Task task) {
        tasks.add(task);
        //System.out.println(task.getTitle() + " is added to the project");
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        //System.out.println(task.getTitle() + " is removed from the project.");
    }

    public void taskIsDone(Task task) {
        if (tasks.contains(task)) {
            task.setDone(true);
            //System.out.println(task.getTitle() + " is done.");
        } else {
            //System.out.println(task.getTitle() + " is not done");
        }
    }

    public Task findTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                //System.out.println(task.getTitle() + " is found.");
                return task;
            }
        }
        return null;
    }

    public String getProjectSummary() {
        return "Project: " + title + "\nDescription: " + description +
                "\nTasks: " + tasks.size();}

}

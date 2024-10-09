package database;

public class Database {
    private String dbName;

    public Database(String dbName) {
        this.dbName = dbName;
    }

    public boolean connect() {
        return true;
    }

}


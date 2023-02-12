package exeptions;

public class DBException extends java.lang.Exception {
    public DBException(String reason) {
        super(reason);
    }

    public DBException() {
        super();
    }
}

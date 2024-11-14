package SQLError;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(message);
    }
}
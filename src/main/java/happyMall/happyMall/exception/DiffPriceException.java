package happyMall.happyMall.exception;

public class DiffPriceException extends RuntimeException{
    public DiffPriceException() {
        super();
    }

    public DiffPriceException(String message) {
        super(message);
    }

    public DiffPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiffPriceException(Throwable cause) {
        super(cause);
    }

    protected DiffPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

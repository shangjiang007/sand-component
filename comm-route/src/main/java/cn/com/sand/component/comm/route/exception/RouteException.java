package cn.com.sand.component.comm.route.exception;

public class RouteException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RouteException() {
        super();
    }

    public RouteException(String s) {
        super(s);
    }

    public RouteException(Throwable cause) {
        super(cause);
    }

    public RouteException(String message, Throwable cause) {
        super(message, cause);
    }

    protected RouteException(String message, Throwable cause,boolean enableSuppression,boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

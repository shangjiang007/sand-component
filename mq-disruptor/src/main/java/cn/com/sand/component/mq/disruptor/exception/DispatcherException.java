package cn.com.sand.component.mq.disruptor.exception;

public class DispatcherException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public DispatcherException()
    {
        super();
    }

    public DispatcherException(String s)
    {
        super(s);
    }

    public DispatcherException(Throwable cause)
    {
        super(cause);
    }

    public DispatcherException(String message, Throwable cause)
    {
        super(message, cause);
    }

    // protected DispatcherException(String message, Throwable cause,boolean
    // enableSuppression,boolean writableStackTrace) {
    // super(message, cause, enableSuppression, writableStackTrace);
    // }

}

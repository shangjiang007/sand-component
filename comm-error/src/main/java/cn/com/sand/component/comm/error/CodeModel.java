package cn.com.sand.component.comm.error;

public class CodeModel
{

    private String errorCode;

    private String errorMsg;

    private String localErrorCode;

    private String localErrorMsg;

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getLocalErrorCode()
    {
        return localErrorCode;
    }

    public void setLocalErrorCode(String localErrorCode)
    {
        this.localErrorCode = localErrorCode;
    }

    public String getLocalErrorMsg()
    {
        return localErrorMsg;
    }

    public void setLocalErrorMsg(String localErrorMsg)
    {
        this.localErrorMsg = localErrorMsg;
    }

    @Override
    public String toString()
    {
        return "CodeModel [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", localErrorCode=" + localErrorCode
                + ", localErrorMsg=" + localErrorMsg + "]";
    }

}

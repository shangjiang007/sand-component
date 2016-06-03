/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-error $Id$ $Revision$ Last Changed by SJ at
 * 2015年11月4日 下午8:16:29 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年11月4日
 * Initailized
 */
package cn.com.sand.component.comm.error;

/**
 *
 * @ClassName ：McError
 * @author : SJ
 * @Date : 2015年11月4日 下午8:16:29
 * @version 2.0.0
 *
 */
public class McError
{
    private String errorCode;
    private String errorSysCode;

    public McError(String errorCode, String errorSysCode)
    {
        this.errorCode = errorCode;
        this.errorSysCode = errorSysCode;
    }

    public final String getErrorCode()
    {
        return errorCode;
    }

    public final void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public final String getErrorSysCode()
    {
        return errorSysCode;
    }

    public final void setErrorSysCode(String errorSysCode)
    {
        this.errorSysCode = errorSysCode;
    }

}

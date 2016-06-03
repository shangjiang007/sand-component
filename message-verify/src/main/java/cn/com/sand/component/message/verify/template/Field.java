/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-message-verify $Id$ $Revision$ Last Changed by SJ at
 * 2015年11月17日 上午10:54:36 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年11月17日
 * Initailized
 */
package cn.com.sand.component.message.verify.template;

/**
 *
 * @ClassName ：Field
 * @author : SJ
 * @Date : 2015年11月17日 上午10:54:36
 * @version 2.0.0
 *
 */
public class Field
{
    private String id;
    private String desc;
    private String verify;
    private String type;
    private String isNull;
    private String size;
    private String errorMsg;

    public final String getId()
    {
        return id;
    }

    public final void setId(String id)
    {
        this.id = id;
    }

    public final String getDesc()
    {
        return desc;
    }

    public final void setDesc(String desc)
    {
        this.desc = desc;
    }

    public final String getVerify()
    {
        return verify;
    }

    public final void setVerify(String verify)
    {
        this.verify = verify;
    }

    public final String getType()
    {
        return type;
    }

    public final void setType(String type)
    {
        this.type = type;
    }

    public final String getIsNull()
    {
        return isNull;
    }

    public final void setIsNull(String isNull)
    {
        this.isNull = isNull;
    }

    public final String getSize()
    {
        return size;
    }

    public final void setSize(String size)
    {
        this.size = size;
    }

    public final String getErrorMsg()
    {
        return errorMsg;
    }

    public final void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}

/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月21日
 * Project : monitor-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月21日 上午9:45:16
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月21日        Initailized
 */
package cn.com.sand.component.monitor.zookeeper.common;

/**
 *
 * @ClassName ：NodeBean
 * @author : SJ
 * @Date : 2016年4月21日 上午9:45:16
 * @version 1.0.0
 *
 */
public class NodeBean
{
    private String id;

    private String desc;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param id
     * @param desc
     */
    public NodeBean(String id, String desc)
    {
        super();
        this.id = id;
        this.desc = desc;
    }

    /**
     * id
     *
     * @return the id
     * @since 1.0.0
     */

    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * desc
     *
     * @return the desc
     * @since 1.0.0
     */

    public String getDesc()
    {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}

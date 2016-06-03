/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年2月22日 下午2:59:47
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年2月22日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.common;

/**
 *
 * @ClassName ：Constants
 * @author : SJ
 * @Date : 2016年2月22日 下午2:59:47
 * @version 2.0.0
 *
 */
public class Constants
{
    /** utf-8 */
    public static final String UTF8 = "utf-8";
    
    public static final String DEFAULT_NAME_SPACE = "sand";

    /** zookeeper目录分割符 */
    public static final String ZK_SEPARATOR_DEFAULT = "/";

    public static final String ZK_SEPARATOR_VERTICAL = "|";

    /** zookeeper中使用时间戳作目录的格式 */
    public static final String ZK_TIME_NODE_FORMAT = "yyyyMMddHHmmss";

    /** zookeeper中总计节点名称 */
    public static final String ZK_NAMESPACE_TOTAL = "total";

    /** zookeeper中详细节点名称 */
    public static final String ZK_NAMESPACE_DETAIL = "detail";

    public static final String PROTOCOL_TCP = "tcp";

    public static final String PROTOCOL_HTTP = "http";

    public static final String CALL_COUNT = "count";

    public static final String ADDR_IP = "ip";

    public static final String ADDR_PORT = "port";

    public static final String ZK_COLON_DEFAULT = ":";
}

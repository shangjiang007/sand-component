/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年5月16日
 * 上午11:27:40 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年5月16日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.core.node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.common.Constants;

/**
 *
 * @ClassName ：NodeHelper
 * @author : SJ
 * @Date : 2016年5月16日 上午11:27:40
 * @version 2.0.0
 *
 */
public class NodeHelper
{
    private static final Logger logger = LoggerFactory.getLogger(NodeHelper.class);

    public static String getNameSpace(String servNode, NodeType type)
    {
        return Constants.ZK_SEPARATOR_DEFAULT + servNode + Constants.ZK_SEPARATOR_DEFAULT + type.value();
    }

    public static String getConfigPath(String nameSpace, String config)
    {
        try
        {
            return nameSpace + Constants.ZK_SEPARATOR_DEFAULT + URLEncoder.encode(config, Constants.UTF8);
        }
        catch (UnsupportedEncodingException e)
        {
            logger.warn("URLEncoder encode: {} fail by {} ", new Object[] { config, e.getMessage() });
        }
        return nameSpace + Constants.ZK_SEPARATOR_DEFAULT + config;
    }
}

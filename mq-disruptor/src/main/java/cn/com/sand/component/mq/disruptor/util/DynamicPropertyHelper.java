/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-util $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月15日 下午8:06:12 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月15日
 * Initailized
 */
package cn.com.sand.component.mq.disruptor.util;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

/**
 *
 * @ClassName ：DynamicPropertyHelper
 * @author : SJ
 * @Date : 2015年10月15日 下午8:06:12
 * @version 2.0.0
 *
 */
public class DynamicPropertyHelper
{
    private static final DynamicPropertyFactory dynamicPropertyFactory = DynamicPropertyFactory
            .getInstance();

    public static DynamicPropertyFactory getDynamicPropertyFactory()
    {
        return dynamicPropertyFactory;
    }

    public static DynamicStringProperty getStringProperty(String propName,
            String defaultValue)
    {
        return getDynamicPropertyFactory().getStringProperty(propName,
                defaultValue);
    }

    public static DynamicIntProperty getIntProperty(String propName,
            int defaultValue)
    {
        return getDynamicPropertyFactory().getIntProperty(propName,
                defaultValue);
    }
}

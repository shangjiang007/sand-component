/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-message-verify $Id$ $Revision$ Last Changed by SJ at
 * 2015年11月17日 上午11:29:22 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年11月17日
 * Initailized
 */
package cn.com.sand.component.message.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.com.sand.component.message.verify.template.Field;
import cn.com.sand.component.message.verify.template.ParseTemplate;

/**
 *
 * @ClassName ：VerifyManager
 * @author : SJ
 * @Date : 2015年11月17日 上午11:29:22
 * @version 2.0.0
 *
 */
public class VerifyManager
{
    public static Map<String, String> verify(String templateId, JSONObject json)
    {
        Map<String, String> veriMap = new HashMap<String, String>();
        List<Field> template = getTemplate(templateId);
        for (int i = 0; i < template.size(); i++)
        {
            Field field = template.get(i);
            boolean verify = Boolean.valueOf(field.getVerify());
            if (verify)
            {
                boolean isNull = Boolean.valueOf(field.getIsNull());
                String id = field.getId();
                String val = (json.get(id) == null) ? "" : json.getString(id);
                if (!isNull && StringUtils.isNotBlank(val))
                {
                    String size = field.getSize();
                    if (StringUtils.isNotBlank(size))
                    {
                        Integer vlen = val.length();
                        String[] len = size.split(",");
                        
                        Integer begin = Integer.valueOf(len[0]);

                        if (len.length == 2)
                        {
                            Integer end = Integer.valueOf(len[1]);
                            if (vlen.compareTo(begin) < 0 || vlen.compareTo(end) > 0)
                            {
                                veriMap.put(id, field.getErrorMsg());
                            }
                        }
                        else
                        {
                            if (vlen.compareTo(begin) < 0)
                            {
                                veriMap.put(id, field.getErrorMsg());
                            }
                        }
                    }
                }
                else
                {
                    veriMap.put(id, field.getErrorMsg());
                }
            }
        }
        return veriMap;
    }

    private static List<Field> getTemplate(String templateId)
    {
        return ParseTemplate.getTemplate(templateId);
    }

    public static String getTemplateId(String tcode, String txnType, String txnSubType)
    {
        return tcode + txnType + txnSubType;
    }

}

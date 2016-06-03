/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-message-verify $Id$ $Revision$ Last Changed by SJ at
 * 2015年11月17日 上午10:50:57 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年11月17日
 * Initailized
 */
package cn.com.sand.component.message.verify.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netflix.config.ConfigurationManager;

/**
 * 
 * @ClassName ：ParseTemplate
 * @author : SJ
 * @Date : 2015年11月17日 上午10:50:57
 * @version 2.0.0
 * 
 */
public class ParseTemplate
{
    private Logger                          logger                    = LoggerFactory.getLogger(ParseTemplate.class);

    private static final String             VERIFY_TEMPLATE_DIRECTORY = "template/";
    private static final String             TEMPLATE_SUFFIX           = ".xml";
    private static Map<String, List<Field>> templateMap               = new HashMap<String, List<Field>>();

    private static ParseTemplate            instance;

    private ParseTemplate()
    {
        scanTemplate();
    }

    public static ParseTemplate getInstance()
    {
        if (instance == null)
        {
            synchronized (ParseTemplate.class)
            {
                if (instance == null)
                {
                    instance = new ParseTemplate();
                }
            }
        }
        return instance;
    }

    public void scanTemplate()
    {
        try
        {
            ConfigurationManager.loadAppOverrideProperties("application.message.verify");
            Iterator<String> keys = ConfigurationManager.getConfigInstance().getKeys("Mc");
            while (keys.hasNext())
            {
                loadTemplateDefinitions(ConfigurationManager.getConfigInstance().getString(keys.next()));
            }
        }
        catch (IOException e)
        {
            logger.error("load config file error...");
        }
    }

    public void loadTemplateDefinitions(String templateFileName)
    {
        InputStream inStream = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String xmlFileName = VERIFY_TEMPLATE_DIRECTORY + templateFileName+TEMPLATE_SUFFIX;

        try
        {
            inStream = ParseTemplate.class.getClassLoader().getResourceAsStream(xmlFileName);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docTree = builder.parse(inStream);

            Element root = docTree.getDocumentElement();
            if ("templates".equalsIgnoreCase(root.getNodeName()))
            {
                NodeList templates = root.getElementsByTagName("template");
                for (int i = 0; i < templates.getLength(); i++)
                {
                    Node template = templates.item(i);
                    if (template.getNodeType() != Node.TEXT_NODE)
                    {
                        String templateId = template.getAttributes().getNamedItem("id").getNodeValue();
                        NodeList fields = template.getChildNodes();
                        List<Field> templateList = new ArrayList<Field>();
                        for (int j = 0; j < fields.getLength(); j++)
                        {
                            Node field = fields.item(j);
                            if (field.getNodeType() != Node.TEXT_NODE)
                            {
                                Field temp = new Field();
                                String id = field.getAttributes().getNamedItem("id").getNodeValue();
                                temp.setId(id);
                                String desc = field.getAttributes().getNamedItem("desc").getNodeValue();
                                temp.setDesc(desc);
                                String verify = field.getAttributes().getNamedItem("verify").getNodeValue();
                                temp.setVerify(verify);
                                String type = field.getAttributes().getNamedItem("type").getNodeValue();
                                temp.setType(type);
                                String isNull = field.getAttributes().getNamedItem("isNull").getNodeValue();
                                temp.setIsNull(isNull);
                                String size = field.getAttributes().getNamedItem("size").getNodeValue();
                                temp.setSize(size);
                                String errorMsg = field.getAttributes().getNamedItem("errorMsg").getNodeValue();
                                temp.setErrorMsg(errorMsg);
                                templateList.add(temp);
                            }
                        }
                        templateMap.put(templateId, templateList);
                    }
                    logger.info("load " + xmlFileName + " success.");
                }
            }
        }
        catch (Exception e)
        {
            throw new IllegalStateException("load " + xmlFileName + " fail...", e);
        }
        finally
        {
            if (inStream != null)
                try
                {
                    inStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    public static List<Field> getTemplate(String templateId)
    {
        List<Field> template = templateMap.get(templateId);
        if (template == null)
        {
            throw new RuntimeException("template is null ---> " + templateId);
        }
        return template;
    }
}

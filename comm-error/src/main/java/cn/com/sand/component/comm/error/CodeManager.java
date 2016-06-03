package cn.com.sand.component.comm.error;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CodeManager
{

    private static Logger                       log            = LoggerFactory.getLogger(CodeManager.class);
    private static Map<String, List<CodeModel>> codeMap        = new HashMap<String, List<CodeModel>>();
    private static final String                 configLocation = "config/code_model.xml";
    private static CodeManager                  code;

    public CodeManager()
    {
        loadBeanDefinitions(configLocation);
    }

    public static CodeManager getInstance()
    {
        if (code == null)
        {
            code = new CodeManager();
        }
        return code;
    }

    private void loadBeanDefinitions(String configLocation)
    {
        InputStream inStream = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            inStream = CodeManager.class.getClassLoader().getResourceAsStream(configLocation);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docTree = builder.parse(inStream);
            Element root = docTree.getDocumentElement();
            if (!root.getNodeName().equalsIgnoreCase("code_config"))
            {
                throw new RuntimeException("error!");
            }
            else
            {
                NodeList nodeSystem = root.getElementsByTagName("system");
                for (int i = 0; i < nodeSystem.getLength(); i++)
                {
                    ArrayList<CodeModel> nodeList = new ArrayList<CodeModel>();
                    Node child = nodeSystem.item(i);
                    if (child.getNodeType() != Node.TEXT_NODE)
                    {
                        String sysId = child.getAttributes().getNamedItem("id").getNodeValue();
                        NodeList errorNodeList = child.getChildNodes();
                        for (int j = 0; j < errorNodeList.getLength(); j++)
                        {
                            CodeModel cm = new CodeModel();
                            Node errorNode = errorNodeList.item(j);
                            if (errorNode.getNodeType() != Node.TEXT_NODE)
                            {
                                cm.setErrorCode(errorNode.getAttributes().getNamedItem("errorCode").getNodeValue());
                                cm.setErrorMsg(errorNode.getAttributes().getNamedItem("errorMsg").getNodeValue());
                                cm.setLocalErrorCode(
                                        errorNode.getAttributes().getNamedItem("localErrorCode").getNodeValue());
                                cm.setLocalErrorMsg(
                                        errorNode.getAttributes().getNamedItem("localErrorMsg").getNodeValue());
                                nodeList.add(cm);
                            }
                        }
                        codeMap.put(sysId, nodeList);
                    }
                }
            }
            log.info("加载错误码配置文件成功...");
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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

    private static CodeModel getCodeModelBySCId(String sysId, String codeId)
    {
        CodeModel cm = null;
        if (codeMap != null && codeMap.size() > 0 && sysId != null && codeId != null)
        {
            List<CodeModel> cmLists = codeMap.get(sysId);
            for (int i = 0; i < cmLists.size(); i++)
            {
                CodeModel tempm = cmLists.get(i);
                if (tempm != null && tempm.getLocalErrorCode().equals(codeId))
                {
                    cm = tempm;
                    break;
                }
            }
        }
        return cm;
    }

    public static ErrorCode getErrorCode(String sysId, String codeId)
    {
        CodeModel hostRetCodeModel = getCodeModelBySCId(sysId, codeId);
        if (hostRetCodeModel == null)
        {
            log.error("系统未配置可转换的错误码");
            throw new RuntimeException("hostRetCodeModel must not be null.");
        }
        String hostRetCode = hostRetCodeModel.getErrorCode();
        String hostRetDesc = hostRetCodeModel.getErrorMsg();
        log.info("[" + sysId + "]错误码转换[" + codeId + " --> " + hostRetCode + "]");

        ErrorCode errorCode = new ErrorCode();
        errorCode.setRetCode(hostRetCode);
        errorCode.setRetDesc(hostRetDesc);
        return errorCode;
    }

    public static CodeModel getCodeModel(String sysId, String codeId)
    {
        CodeModel hostRetCodeModel = getCodeModelBySCId(sysId, codeId);
        if (hostRetCodeModel == null)
        {
            log.error("系统未配置可转换的错误码,sysId:" + sysId + ",codeId:" + codeId);
            throw new RuntimeException("hostRetCodeModel must not be null.");
        }
        log.info("错误码转换 ---> " + hostRetCodeModel.toString());
        return hostRetCodeModel;
    }

    public static ErrorCode getErrorCode(String sysId, String codeId, String defCodeMsg)
    {
        CodeModel hostRetCodeModel = getCodeModelBySCId(sysId, codeId);
        // 默认返回原错误码和描述
        String hostRetCode = codeId;
        String hostRetDesc = defCodeMsg;

        // 如果配置文件中找到了转换的错误码, 则进行转换
        if (hostRetCodeModel != null)
        {
            hostRetCode = hostRetCodeModel.getErrorCode();
            hostRetDesc = hostRetCodeModel.getErrorMsg();
            log.info("[" + sysId + "]错误码转换[" + codeId + " --> " + hostRetCode + "]");
        }
        else
        {
            log.error("错误码[" + codeId + "]转换失败, 在[" + sysId + "]平台的错误码配置中未找到该错误码, 返回原错误码");
        }
        ErrorCode errorCode = new ErrorCode();
        errorCode.setRetCode(hostRetCode);
        errorCode.setRetDesc(hostRetDesc);
        return errorCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.sandpay.multichannel.core.error.errorcode.CodeManager#getErrorCode
     * (java.lang.String)
     */
    public static ErrorCode getMcErrorCode(String codeId)
    {
        return getErrorCode(ErrorConstants.ERROR_SYSID_MCRESPONSECODE, codeId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sandpay.multichannel.core.error.errorcode.CodeManager#
     * getMcRefuErrorCode(java.lang.String)
     */
//    public static ErrorCode getMcRefuErrorCode(String codeId)
//    {
//        return getErrorCode(ErrorConstants.ERROR_SYSID_MCREFUSECODE, codeId);
//    }
//
//    public static CodeModel getMcRefuCode(String codeId)
//    {
//        return getCodeModel(ErrorConstants.ERROR_SYSID_MCREFUSECODE, codeId);
//    }

    public static CodeModel getMcRespCode(String codeId)
    {
        return getCodeModel(ErrorConstants.ERROR_SYSID_MCRESPONSECODE, codeId);
    }

    public static CodeModel getMcRespCode(String codeId, String errorSysCode)
    {
        return getCodeModel(errorSysCode, codeId);
    }
}

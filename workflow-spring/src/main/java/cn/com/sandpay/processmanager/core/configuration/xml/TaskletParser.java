package cn.com.sandpay.processmanager.core.configuration.xml;

import java.util.List;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class TaskletParser
{

    private static final String TASKLET_REF_ATTR = "ref";

    private static final String REF_ELE = "ref";

    private static final String TASKLET_PARAMS_ATTR = "params";

    private static final String TASKLET_RETURNS_ATTR = "returns";

    private static final String TASKLET_ROUTE_ATTR = "route";

    public void parseTasklet(Element stepElement, Element taskletElement, AbstractBeanDefinition bd,
            ParserContext parserContext)
    {

        bd.setBeanClass(StepParserStepFactoryBean.class);
        bd.setAttribute("isNamespaceStep", true);

        String taskletRef = taskletElement.getAttribute(TASKLET_REF_ATTR);
        // 新增params、returns
        String params = stepElement.getAttribute(TASKLET_PARAMS_ATTR);
        String returns = stepElement.getAttribute(TASKLET_RETURNS_ATTR);
        String route = stepElement.getAttribute(TASKLET_ROUTE_ATTR);
        if (params != null && !"".equals(params))
        {
            bd.getPropertyValues().addPropertyValue("params", params);
        }
        if (returns != null && !"".equals(returns))
        {
            bd.getPropertyValues().addPropertyValue("returns", returns);
        }
        if (route != null && !"".equals(route))
        {
            bd.getPropertyValues().addPropertyValue("route", route);
        }
        List<Element> refElements = DomUtils.getChildElementsByTagName(taskletElement, REF_ELE);

        validateTaskletAttributesAndSubelements(taskletElement, parserContext, taskletRef, refElements);

        BeanMetadataElement bme = null;
        if (StringUtils.hasText(taskletRef))
        {
            bme = new RuntimeBeanReference(taskletRef);
        }
        else if (refElements.size() == 1)
        {
            bme = (BeanMetadataElement) parserContext.getDelegate().parsePropertySubElement(refElements.get(0), null);
        }

        if (bme != null)
        {
            bd.getPropertyValues().addPropertyValue("tasklet", bme);
        }

        handleTaskletElement(taskletElement, bd, parserContext);
    }

    private void validateTaskletAttributesAndSubelements(Element taskletElement, ParserContext parserContext,
            String taskletRef, List<Element> refElements)
    {
        int total = (StringUtils.hasText(taskletRef) ? 1 : 0) + refElements.size();

        StringBuilder found = new StringBuilder();
        if (total > 1)
        {
            if (StringUtils.hasText(taskletRef))
            {
                found.append("'" + TASKLET_REF_ATTR + "' attribute, ");
            }
            if (refElements.size() == 1)
            {
                found.append("<" + REF_ELE + "/> element, ");
            }
            else if (refElements.size() > 1)
            {
                found.append(refElements.size() + " <" + REF_ELE + "/> elements, ");
            }
            found.delete(found.length() - 2, found.length());
        }
        else
        {
            found.append("None");
        }

        String error = null;
        if (total != 1)
        {
            error = "must have exactly";
        }

        if (error != null)
        {
            parserContext.getReaderContext()
                    .error("The <" + taskletElement.getTagName() + "/> element " + error + " one of: '"
                            + TASKLET_REF_ATTR + "' attribute, </> element, </> attribute, or <" + REF_ELE
                            + "/> element.  Found: " + found + ".", taskletElement);
        }
    }

    private void handleTaskletElement(Element taskletElement, AbstractBeanDefinition bd, ParserContext parserContext)
    {
        bd.setRole(BeanDefinition.ROLE_SUPPORT);
        bd.setSource(parserContext.extractSource(taskletElement));
    }

}

/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-jetty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月14日 下午7:47:14 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月14日
 * Initailized
 */
package cn.com.sand.component.serve.jetty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 *
 * @ClassName ：ServletContextHandlerFactory
 * @author : SJ
 * @Date : 2015年10月14日 下午7:47:14
 * @version 2.0.0
 *
 */
public class ServletContextHandlerFactory
{
    private static ServletContextHandlerFactory   instance;
    private String                                contextPath;
    private Map<Class<?>, String> servletMap = new HashMap<Class<?>, String>();
    private Class<? extends Servlet>              servlet;
    private String                                pathSpec;
    private static ServletContextHandler          context;

    private ServletContextHandlerFactory()
    {
    }

    public static synchronized ServletContextHandlerFactory create()
    {
        if (instance == null)
        {
            instance = new ServletContextHandlerFactory();
        }
        if (context == null)
        {
            context = new ServletContextHandler();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public ServletContextHandler build()
    {
        if (contextPath != null)
        {
            context.setContextPath(contextPath);
        }
        if (servlet != null && pathSpec != null)
        {
            context.addServlet(servlet, pathSpec);
        }
        if (servletMap != null)
        {
            for (Map.Entry<Class<?>, String> servletEntry : servletMap
                    .entrySet())
            {
                context.addServlet((Class<? extends Servlet>) servletEntry.getKey(),
                        servletEntry.getValue());
            }
        }

        return context;
    }

    public ServletContextHandlerFactory contextPath(String contextPath)
    {
        this.contextPath = contextPath;
        return this;
    }

    public ServletContextHandlerFactory servletMap(
            Map<Class<?>, String> servletMap)
    {
        this.servletMap = servletMap;
        return this;
    }

    public ServletContextHandlerFactory servlet(
            Class<? extends Servlet> servlet)
    {
        this.servlet = servlet;
        return this;
    }

    public ServletContextHandlerFactory pathSpec(String pathSpec)
    {
        this.pathSpec = pathSpec;
        return this;
    }
}

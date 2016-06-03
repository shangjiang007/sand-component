package cn.com.sand.component.comm.route.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.ProxyHelper;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.comm.route.api.Route;
import cn.com.sand.component.comm.route.api.Rule;
import cn.com.sand.component.comm.route.exception.RouteException;

public abstract class CamelRoute implements Route
{
    private static Logger logger       = LoggerFactory
            .getLogger(CamelRoute.class);
    private CamelContext  camelContext = new DefaultCamelContext();

    public CamelRoute()
    {
        this.init();
    }

    public void init()
    {
        try
        {
            this.camelContext.addRoutes(this.getRouteBuilder());
        }
        catch (Exception e)
        {
            logger.error("camel route init fail, cause by:", e);
        }
    }

    public abstract RouteBuilder getRouteBuilder();

    public <T> T route(Rule rule, Class<T> clazz) throws RouteException
    {
        try
        {
            Endpoint endpoint = camelContext.hasEndpoint(rule.getRule());
            if (endpoint == null)
            {
                return null;
            }
            return ProxyHelper.createProxy(endpoint, clazz);
        }
        catch (Exception e)
        {
            throw new RouteException(e);
        }
    }

    public void start()
    {
        try
        {
            this.camelContext.start();
        }
        catch (Exception e)
        {
            logger.error("camel route start fail, cause by:", e);
        }
    }

    public void stop()
    {
        try
        {
            this.camelContext.stop();
        }
        catch (Exception e)
        {
            logger.error("camel route start fail, cause by:", e);
        }
    }
}

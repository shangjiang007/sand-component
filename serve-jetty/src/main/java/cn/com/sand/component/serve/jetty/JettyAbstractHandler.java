/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-jetty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月12日 下午2:09:45 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月12日
 * Initailized
 */
package cn.com.sand.component.serve.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @ClassName ：JettyAbstractHandler
 * @author : SJ
 * @Date : 2015年10月12日 下午2:09:45
 * @version 2.0.0
 *
 */
public abstract class JettyAbstractHandler extends AbstractHandler
{
    private static final String REQ_MSG_HEAD = "request";

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
     * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
                    throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");
        String reqMsg = request.getParameter(REQ_MSG_HEAD);
        String respMsg = process(reqMsg);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println(respMsg);
    }

    public abstract String process(String req);
}

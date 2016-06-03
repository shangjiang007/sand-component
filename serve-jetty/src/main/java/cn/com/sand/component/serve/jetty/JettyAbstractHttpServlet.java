/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-jetty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月14日 下午7:13:19 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月14日
 * Initailized
 */
package cn.com.sand.component.serve.jetty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName ：JettyAbstractHttpServlet
 * @author : SJ
 * @Date : 2015年10月14日 下午7:13:19
 * @version 2.0.0
 *
 */
public abstract class JettyAbstractHttpServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final String REQ_MSG_HEAD     = "request";

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        super.doGet(req, resp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String request = req.getParameter(REQ_MSG_HEAD);
        String response = process(request);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(response);
        out.flush();
        out.close();
    }

    public abstract String process(String request);
}

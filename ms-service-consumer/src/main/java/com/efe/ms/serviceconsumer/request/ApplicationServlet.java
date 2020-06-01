package com.efe.ms.serviceconsumer.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义serlvet（需要在应用入口application类上添加“@ServletComponentScan”，才能生效）
 * @author liutianlong
 *
 */
@WebServlet(urlPatterns="/myServlet/*",description="自定义servlet")
public class ApplicationServlet extends HttpServlet
{
	private static final long serialVersionUID = 3767637015488554509L;
	
	protected final static Logger logger = LoggerFactory.getLogger(ApplicationServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException
    {
		logger.debug("自定义servlet doGet执行...");
    }   

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException
    {
		logger.debug("自定义servlet doPost执行...");
    }        

}

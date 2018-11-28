package com.gz.efood.controller.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class CheckoutAnalyzer
 *
 */
@WebListener
public class CheckoutAnalyzer implements ServletRequestListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public CheckoutAnalyzer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	ServletContext context = se.getSession().getServletContext();
    	Map<HttpSession, Long> m  = (Map<HttpSession, Long>) context.getAttribute("SessionCreatedTime");
    	if(m == null) {
    		m = new HashMap<>();
    		context.setAttribute("SessionCreatedTime", m);
    	}
    	m.put(se.getSession(), new Date().getTime());
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre)  { 
       	ServletContext context = sre.getServletContext();
       	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    	//context.setAttribute("CartCreatedTime", ((int)context.getAttribute("ServiceUsed"))+1);
    	Map<HttpSession, Long> m  = (Map<HttpSession, Long>) context.getAttribute("SessionCreatedTime");
    	Set<Long> timeSet = (Set<Long>) context.getAttribute("timeSet");
    	if(timeSet == null) {
    		timeSet = new HashSet<>();
    		context.setAttribute("timeSet", timeSet);
    	}
    	Long createTime = 0L;
    	createTime = (Long) m.get(request.getSession());
 
    	String session_id = request.getSession().getId();
    	String servletPath = request.getServletPath();
    	if(servletPath == "Checkout" && createTime != 0L)
    	{
    		Long time = new Date().getTime() - createTime;
    		timeSet.add(time);
    	}
		
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }
	
}

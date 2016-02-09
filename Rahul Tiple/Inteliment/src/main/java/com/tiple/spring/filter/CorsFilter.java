package com.tiple.spring.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.tiple.spring.service.ServiceObject;
import com.tiple.spring.service.TokenService;

@Component
public class CorsFilter implements Filter {
		
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		
		 final HttpServletRequest request = (HttpServletRequest) req;
	     final String authHeader = request.getHeader("Authorization");
	     System.out.println("token header :: "+authHeader);
	    // 
	    response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-xsrf-token,Origin, X-Requested-With, Accept,Authorization");
		
	     Set tokenlist= (Set)context.getAttribute("tokenlist");
	     System.out.println("tokenlist size= "+tokenlist.size());
	     if(authHeader!=null && tokenlist.contains(authHeader)){
	    	 try{
	    		 TokenService tokenService= ServiceObject.getTokenObject();
	    		 tokenService.parseJWT(authHeader);
	    		 chain.doFilter(req, res);
	    	 }catch(Exception ex){
	    		 response.getWriter().println(" your token is expired so login to http://host/Inteliment/rest/person/login to create the token ");
	    	 }
	     }else{
	    	 response.getWriter().println("Invalid Login (Userid and Pwd) please use the link http://host/Inteliment/rest/person/login");
	     }
	     
			/*if(false){ 
			response.getWriter().println("wrong response");
		}else{
			chain.doFilter(req, res);
		}*/
		
	}
	ServletContext context=null;
	public void init(FilterConfig filterConfig) {
		 context = filterConfig.getServletContext(); 
	}

	public void destroy() {}

}
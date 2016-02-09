package com.tiple.spring.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.tiple.spring.controller.EmpRestURIConstants;
import com.tiple.spring.controller.EmployeeController;
import com.tiple.spring.model.Employee;
import com.tiple.spring.model.ParagraphHolder;
import com.tiple.spring.model.ParagraphWordHolder;
import com.tiple.spring.service.ServiceObject;
import com.tiple.spring.service.TokenService;
import com.tiple.spring.util.TokenGenerator;

import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
		
		/*
	 * url is :- http://localhost:8080/SpringRestExample/rest/person/login
	 * 
	 * Post data is :- {"id":9999,"name":"Rahul Tiple","password":null,"createdDate":1454501347860}
	 */
	@Autowired
    private WebApplicationContext webApplicationContext;
	@RequestMapping(value = EmpRestURIConstants.LOGIN, method = RequestMethod.POST,consumes="application/json")
	public @ResponseBody String login(@RequestBody Employee emp) {
		logger.info("Start login... "+emp);
		System.out.println("------inside the login------");
		if(emp.getName() !=null && emp.getPassword()!=null){
			TokenService tokenService= ServiceObject.getTokenObject();
			String token =tokenService.createJWT(emp.getName(), emp.getPassword(),"login",100000);
			tokenlist.add(token);
			//request.setAttribute("tokenlist", tokenlist);
			 
			if ( webApplicationContext == null ) {
				 throw new RuntimeException("WebServiceContext not injected.");
			 }
				ServletContext sc = webApplicationContext.getServletContext();
				
				 if ( sc == null ) { 
				   throw new RuntimeException("ServletContext is null.");
				 }
				 sc.setAttribute("tokenlist", tokenlist);
			return "{accesstoken:"+token+"}";
		}else{
			return "Input Username or Password may be null ";
		}
	}
	/*
	 * url dummy is :- 
	 */
	@RequestMapping(value = EmpRestURIConstants.TOP_URL, method = RequestMethod.GET,produces = {"text/csv", "application/*"})
	public @ResponseBody String dummy(@PathVariable("count") int count) {
		logger.info("Start login... "+count);
		System.out.println("------inside the dummy------"+count);
		//return  "a,b,c\n1,2,3\n3,4,5";
		System.out.println("List "+list.size());
		if(count > list.size()){
			return "you are given top parameter above the size of paragraph please reduce the top parameter";	
		}else{
			return csvConversion(list.subList(0, count));
		}
	}
	@RequestMapping(value = EmpRestURIConstants.SEARCH_URL, method = RequestMethod.POST,consumes="application/json")
	public @ResponseBody  ParagraphWordHolder searchText(@RequestBody ParagraphHolder holder) {
		logger.info("Start login... "+holder);
		System.out.println("------inside the searchText-");
		System.out.println("------inside the dummy------"+holder.getSearchText().length);
		String[] arrString=holder.getSearchText();
		List<Map> listmap = new ArrayList<Map>();
		for(String word:arrString){
			Map localmap = new HashMap<String,String>();
			if(map.get(word)!=null){
				localmap.put(word, map.get(word));
			}else{
				localmap.put(word, 0);
			}
			listmap.add(localmap);
			
		}
		ParagraphWordHolder paragraphWordHolder = new ParagraphWordHolder();
		paragraphWordHolder.setCounts(listmap);
		return  paragraphWordHolder;
	}
	String csvConversion( List<Entry<String, Integer>> listforCSV){
		String csvString="";
		for(Map.Entry<String, Integer> entry:listforCSV){
			csvString=csvString+entry.getKey()+","+entry.getValue()+"\n";
		}
		return csvString;
	}
	static Map<String,Integer> map = null;
	static Set<Entry<String, Integer>> set =null;
	static List<Entry<String, Integer>> list = null;
	static Set<String> tokenlist = null;
	static{
		tokenlist =new HashSet<String>();
		map = new HashMap<String,Integer>();
		set = map.entrySet();
		list = new ArrayList<Entry<String, Integer>>(set);
		try{
			list=getreadFile();
			
		}catch(IOException ioe){
			System.out.println("File not found exception "+ioe);
		}
		System.out.println("list size : "+list.size());
	}
	static  List<Entry<String, Integer>> getreadFile() throws IOException{
		File file = new File("C://emp.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		
		while( (line = br.readLine())!= null ){
		        // \\s+ means any number of whitespaces between tokens
		    String [] tokens = line.split("\\s+");
		  
		    for(String token:tokens){
		    	if(map.get(token) ==null ){
		    		
		    		map.put(token, 1);
		    	}else{
		    		
		    		int counttoken=map.get(token);
		    		counttoken++;
		    		map.put(token,counttoken);
		    	}
		    	//System.out.println(token+":"+map.get(token));
		    	
		    }
		}
		System.out.println("Map  size:"+map.size());
		Set<Entry<String, Integer>> set = map.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
      
        System.out.println("list=> "+list);
		return list;
	}
	
}

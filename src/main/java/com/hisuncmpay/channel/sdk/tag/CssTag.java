package com.hisuncmpay.channel.sdk.tag;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;



public class CssTag extends TagSupport {

	
	private String value = "";
	
	private String needCache;

	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doEndTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		StringBuffer outStr = new StringBuffer(100);
//		没有反向解析，用的是相对路径
	
			String path = request.getContextPath();
			if(!"/".equals(path)){
				outStr.append(path);
			}
		
		JspWriter out = pageContext.getOut();		
		String Strvalue = getValue();
		if (Strvalue!= null && Strvalue.length()>0) {
			String tempUrl = "";
			tempUrl = getValue();			
			File jsFile = new File(pageContext.getServletContext().getRealPath(tempUrl));
			long timestamp = jsFile.lastModified();
			Date when = new Date(timestamp);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssS");
			String result = sdf.format(when); 
			String temp = "";
			if("false".equalsIgnoreCase(needCache)){
				temp = getValue();
			}else{
				temp = getValue().concat("?t="+result);
			}
			outStr.append(temp);
		}		
		try {
			out.print(outStr.toString());
		} catch (IOException e) {
			
			return EVAL_PAGE;
		}
		return EVAL_PAGE;
	}

	public String getNeedCache() {
		return needCache;
	}

	public void setNeedCache(String needCache) {
		this.needCache = needCache;
	}

	
}

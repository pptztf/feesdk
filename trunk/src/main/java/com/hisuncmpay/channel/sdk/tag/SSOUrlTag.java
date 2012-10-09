package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;


//没有实现逻辑

public class SSOUrlTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7846993615480289385L;

	private String value = "";
	
	
	
	
	//private Logger log = HiLog.getLogger("TAGLIB.trc");



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}

	
	

	public int doStartTag() {		
		JspWriter out = pageContext.getOut();
		try {
			out.print(value.toString());
		} catch (IOException e) {
			
			return EVAL_PAGE;
		}
		return EVAL_PAGE;
	}



	
	
}


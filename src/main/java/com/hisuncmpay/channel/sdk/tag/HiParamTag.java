package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class HiParamTag extends TagSupport{
	
	private String name;
	private String defaultValue="";
	
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String val=defaultValue;
		System.out.println("!!!!!!!!!!!!!!!!!!!SDK不支持此TAG!!!!!!!!!!!!!!!!!!!!!!");	
		try {
			out.print(val);
		} catch (IOException e) {
			throw new JspException(e);
		}
		super.doEndTag();
		return this.EVAL_PAGE;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}

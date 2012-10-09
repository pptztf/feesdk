package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;



public class OcxSetupTag extends TagSupport {
 	

	private String name="LOGIN";
	private String ffloginurl ="https://cmpay.10086.cn/ocx/npcmpay.exe";
	private String ieloginurl ="https://cmpay.10086.cn/ocx/SSClient.exe";
	
	private String browser = "Firefox";

	public int doEndTag() throws JspException {
		
		
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		String inputUri = request.getRequestURL().toString().trim();
		
		if(inputUri.indexOf("172.16.49.32")!=-1||inputUri.indexOf("211.138.236.210")!=-1){
			 ffloginurl ="https://uatcmpay.10086.cn/ocx/npcmpay.exe";
			 ieloginurl ="https://uatcmpay.10086.cn/ocx/SSClient.exe";
		}else if(inputUri.indexOf("192.168.1.234")!=-1){
			 ffloginurl ="https://192.168.1.234:32033/ocx/npcmpay.exe";
			 ieloginurl ="https://192.168.1.234:32033/ocx/SSClient.exe";
		}
		JspWriter out = pageContext.getOut();
		String userAgent = request.getHeader("User-Agent");		
		if(userAgent!= null && userAgent.indexOf(browser)!=-1){
			
				try {
					out.print(ffloginurl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}else{
			try {
				out.print(ieloginurl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getFfloginurl() {
		return ffloginurl;
	}

	public void setFfloginurl(String ffloginurl) {
		this.ffloginurl = ffloginurl;
	}

	public String getIeloginurl() {
		return ieloginurl;
	}

	public void setIeloginurl(String ieloginurl) {
		this.ieloginurl = ieloginurl;
	}
	
	
	





	
}


package com.hisuncmpay.channel.sdk.tag;
import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class OcxTag extends TagSupport {
 	
	//是否开启浏览器判断的开关
	private String flag = "";
		
	//控件id
	private String ocxid;
	
	//其他代码
	private String other;
	
	private String name="";

	
	private final static String loginOcx = "LOGIN";
	private final static String payOcx = "PAY";
	private final static String moneyOcx = "AMT";
	
	private int type=0;
	
	private String tabindex;
	
	private final static String LOGINOCXID="SSClient1";
	private final static String PAYOCXID="PSW_OCX_MBL";
	private final static String AMTOCXID="AMT";
	
	
	private final static String ffcmpayid="application/x-npClient-mfc";
	private final static String ffamtid="application/x-npcmpaym-mfc";
	
	private String loginclassid="CLSID:B64A48FE-7B76-47a9-994A-9024D4AEA39F";
	private String payclassid="CLSID:B64A48FE-7B76-47a9-994A-9024D4AEA39F";
	private String amtclassid="CLSID:C15DDF55-9AE3-490A-A6F5-E63020698D5C";
	
	
	public String getAmtclassid() {
		return amtclassid;
	}




	public void setAmtclassid(String amtclassid) {
		this.amtclassid = amtclassid;
	}




	public String getLoginclassid() {
		return loginclassid;
	}




	public void setLoginclassid(String loginclassid) {
		this.loginclassid = loginclassid;
	}




	public String getPayclassid() {
		return payclassid;
	}




	public void setPayclassid(String payclassid) {
		this.payclassid = payclassid;
	}




	public String getFlag() {
		return flag;
	}




	public void setFlag(String flag) {
		this.flag = flag;
	}


	private String browser = "Firefox";
	
	
	
	public int doEndTag() throws JspException {
		if(other == null){
			other="";
		}
		if("".equals(tabindex) || tabindex==null){
			tabindex="";
		}else{
			tabindex="tabindex=\""+tabindex+"\"";
		}
		String loginocxid=LOGINOCXID;
		String payocxid=PAYOCXID;
		String amtocxid=AMTOCXID;
		if(ocxid!=null && ocxid.length()>0){
			loginocxid=ocxid;
			payocxid=ocxid;
			amtocxid=ocxid;
		}
		if(loginOcx.equalsIgnoreCase(name)){
			type=0;
			out(type,loginocxid,payocxid,amtocxid);
		}else if(payOcx.equalsIgnoreCase(name)){
			type=1;
			out(type,loginocxid,payocxid,amtocxid);
		}else if(moneyOcx.equalsIgnoreCase(name)){
			type=2;
			out(type,loginocxid,payocxid,amtocxid);
		}else{
			type=3;
			out(type,loginocxid,payocxid,amtocxid);
		}
		
		return EVAL_PAGE;
	}
	
	
	private void out(int type,String loginocxid,String payocxid,String amtocxid){
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		JspWriter out = pageContext.getOut();
		String userAgent = request.getHeader("User-Agent");
		if(userAgent!= null && userAgent.indexOf(browser)!=-1){
			if(type == 0){
				try {
					out.print("<embed  width=\"125\" height=\"25\" "+tabindex+" type=\""+ffcmpayid+"\" id=\""+loginocxid+"\"   "+other+"></embed>");
				} catch (IOException e) {
				
				}
			}else if(type ==1){
				try {
					out.print("<embed  width=\"125\" height=\"25\" "+tabindex+" type=\""+ffcmpayid+"\" id=\""+payocxid+"\"   "+other+"></embed>");
				} catch (IOException e) {
				
				}
			}else{
				try {
					out.print("<embed  width=\"125\" height=\"25\" "+tabindex+" type=\""+ffamtid+"\" id=\""+amtocxid+"\"   "+other+"></embed>");
				} catch (IOException e) {
				
				}
			}
		}else{
			if(type == 0){
				try {
					out.print("<object width=\"110\" height=\"25\" "+tabindex+" codebase=\"https://cmpay.10086.cn/ocx/cmpaySSClient.cab#version=1,0,1,3\" classid=\""+loginclassid+"\" id=\""+loginocxid+"\"  "+other+"></object>");
				} catch (IOException e) {
				
				}
			}else if(type ==1){
				try {
					out.print("<object width=\"110\" height=\"25\" "+tabindex+" codebase=\"https://cmpay.10086.cn/ocx/cmpaySSClient.cab#version=1,0,1,3\" classid=\""+payclassid+"\" id=\""+payocxid+"\"  "+other+"></object>");
				} catch (IOException e) {
				
				}
			}else{
				try {
					out.print("<object width=\"110\" height=\"25\" "+tabindex+" codebase=\"https://cmpay.10086.cn/ocx/cmpaySSClient.cab#version=1,0,1,3\" classid=\""+amtclassid+"\" id=\""+amtocxid+"\"  "+other+"></object>");
				} catch (IOException e) {
				
				}
			}
			
		}
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getOcxid() {
		return ocxid;
	}




	public void setOcxid(String ocxid) {
		this.ocxid = ocxid;
	}




	public String getOther() {
		return other;
	}




	public void setOther(String other) {
		this.other = other;
	}




	public String getTabindex() {
		return tabindex;
	}




	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}






	
}


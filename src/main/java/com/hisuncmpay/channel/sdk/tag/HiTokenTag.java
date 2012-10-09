package com.hisuncmpay.channel.sdk.tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * ·ÀÖ¹ÖØ¸´Ìá½»
 * @author ThinkPad
 *
 */
public class HiTokenTag extends TagSupport {
	
	private boolean recharge=false;

	public void setRecharge(boolean recharge) {
		this.recharge = recharge;
	}
	
	public int doEndTag() throws JspException {

		String key = "TOKEN";
		if(recharge){
			key = "TOKEN1";
		}
		String tokenStr = String.valueOf(System.currentTimeMillis());
		HttpSession session = pageContext.getSession();
		HashMap Temp = (HashMap)session.getAttribute("TEMP");
		if(Temp == null){
			Temp = new HashMap(); 
		}
		Temp.put(key, tokenStr);
		session.setAttribute("TEMP", Temp);		
		JspWriter out = this.pageContext.getOut();
		try {
			if (tokenStr != null && tokenStr.length() > 0) {
				out.println("<input type=\"hidden\" name=\"token\" id=\"token\" value=\""
								+ tokenStr + "\" />");
			} else {
				out.println("<input type=\"hidden\" name=\"token\" id=\"token\" value=\"\"  />");
			}

		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {

		return super.doStartTag();
	}
}

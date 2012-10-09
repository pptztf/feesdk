package com.hisuncmpay.channel.sdk.tag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

public class Hash2JsonTag extends BodyTagSupport {

	private String key;

	

	public String getkey() {
		return key;
	}

	public void setkey(String key) {
		this.key = key;
	}

	public int doEndTag() {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		JspWriter out = this.pageContext.getOut();
		Object end_value = null;
		Object value = null;
		String keys[] = key.split("\\.");
	
		int length = keys.length;
		if (keys != null && length > 0) {
			value = request.getAttribute(keys[0]);
		} else {
			return EVAL_PAGE;
		}
		try {
			if (length == 1) {
				end_value = value;
			} else {
				int j = 1;
				for (int i = 1; i < (length - 1); i++) {
					Map map = (Map) value;
					value = map.get(keys[i]);
					j = i + 1;
				}
				end_value = ((Map) value).get(keys[j]);
				
			}
			Object obj = null;
			if (end_value instanceof Map) {
				obj = (JSONObject) JSONObject.fromObject(end_value);
			} else {
				obj = end_value;
			}
			if (obj == null) {
				return EVAL_PAGE;
			}
			out.print(obj.toString());
			
		} catch (Exception e) {
			
			return EVAL_PAGE;
		}
		return EVAL_PAGE;
	}
}


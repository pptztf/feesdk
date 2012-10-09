package com.hisuncmpay.channel.sdk.tag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class OverrideTag extends BodyTagSupport {
	private static final long serialVersionUID = -8379959647039117369L;

	// private Logger _log = HiLog.getErrorLogger("taglib.log");
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean increment=true;
	
	
	public int doStartTag() throws JspException {
		 return  EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		BodyContent b = getBodyContent();
		String varName = BlockTag.getOverrideVariableName(this.name);
		if (b == null) {
			return Tag.EVAL_PAGE;
		}
		
		StringBuffer sb = new StringBuffer(b.getString());
		if(increment){
			Object obj = this.pageContext.getRequest().getAttribute(varName);
			if (obj != null) {
				sb.append((String) this.pageContext.getRequest().getAttribute(
						varName));				
			}
		}
		this.pageContext.getRequest().setAttribute(varName,sb.toString());
		return Tag.EVAL_PAGE;
		
	}
	
	public boolean isIncrement() {
		return increment;
	}

	public void setIncrement(boolean increment) {
		this.increment = increment;
	}

	private boolean isOverrided() {
		String varName = BlockTag.getOverrideVariableName(this.name);
		return (this.pageContext.getRequest().getAttribute(varName) != null);
	}

	private String getOverriedContent() {
		String varName = BlockTag.getOverrideVariableName(this.name);
		return ((String) this.pageContext.getRequest().getAttribute(varName));
	}
}


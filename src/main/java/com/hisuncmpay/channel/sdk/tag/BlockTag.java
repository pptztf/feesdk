package com.hisuncmpay.channel.sdk.tag;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;



public class BlockTag extends BodyTagSupport
{
  private static final long serialVersionUID = -8246166191638588615L;
  
  private String name;
  public static final String BLOCK = "__jsp_override__";

  public static String getOverrideVariableName(String name)
  {
    return BLOCK + name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }

  public int doStartTag()  throws JspException
  {
	  return ((getOverriedContent() == null) ? EVAL_BODY_INCLUDE :SKIP_BODY);
  }

  public int doEndTag()throws JspException
  {
    String overriedContent = getOverriedContent();
    
    
    if (overriedContent == null){
      return Tag.EVAL_PAGE;
    }
    try
    {
      this.pageContext.getOut().write(overriedContent);
    } catch (IOException e) {
      
      throw new JspException("write overridedContent occer IOException,block name:" + this.name, e);
    }
    return Tag.EVAL_PAGE;
  }

  private String getOverriedContent() {
	if(!"*".equals(this.name)){
		String varName = getOverrideVariableName(this.name);
		return ((String)this.pageContext.getRequest().getAttribute(varName));
	}else{
		StringBuffer sb = new StringBuffer();
		Enumeration em = this.pageContext.getRequest().getAttributeNames();
		while(em.hasMoreElements()){
			String key = (String)em.nextElement();
			
			if(key.indexOf(BLOCK)!= -1){				
				sb.append(this.pageContext.getRequest().getAttribute(key));
				
			}
		}
		return sb.toString();
	}
  }
}



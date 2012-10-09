package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;




public class HiConvertTag extends BodyTagSupport {
	private String _value = "";
	private String _defaultValue = "";
	private String _name;
	private HiConvert _convert = HiDBConvert.getInstance();
	

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		
		String convertVal = this._convert.convert(pageContext, _name, _value);
		if(convertVal == null)
			convertVal = this.getDefaultValue();
		
		try {
			out.print(convertVal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.doEndTag();
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		return EVAL_BODY_INCLUDE;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) throws JspException {
		this._value = (String) ExpressionEvaluatorManager.evaluate("value",
				value, Object.class, this, pageContext);
		if (_value == null || _value.equals(""))
			_value = _defaultValue;
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	public void setDefaultValue(String defaultValue) throws JspException {
		_defaultValue = defaultValue;
		if (_value == null || _value.equals(""))
			_value = _defaultValue;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

}


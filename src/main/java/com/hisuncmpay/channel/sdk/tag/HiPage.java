package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;





public class HiPage extends TagSupport {
	private final static String KEY_TOFIRST="public.page.tofirst";//跳到首页
	private final static String KEY_FIRST="public.page.first"; //首页
	private final static String KEY_TOPRE="public.page.toprevious";//跳到上一页
	private final static String KEY_PREVIOUS="public.page.previous";//上一页
	private final static String KEY_TONEXT="public.page.tonext"; //跳到下一页
	private final static String KEY_NEXT="public.page.next"; //下一页
	private final static String KEY_TOLAST="public.page.toend"; //跳到尾页
	private final static String KEY_LAST="public.page.end"; //尾页
	private final static String KEY_GOTO="public.page.goto"; //转到
	private final static String KEY_REFRESH="public.page.refresh"; //
	
	public String _output;
	public String _txncod;
	public String _fields=null;

	public String getOutput() {
		return _output;
	}

	public void setOutput(String output) {
		this._output = output;
	}

	public String getTxncod() {
		return _txncod;
	}

	public void setTxncod(String txncod) {
		this._txncod = txncod;
	}

	public String getfields() {
		return _fields;
	}

	public void setfields(String _fields) {
		this._fields = _fields;
	}

	public int doEndTag() throws JspException {
		ServletRequest request = pageContext.getRequest();
		HashMap map = (HashMap) request.getAttribute("ETF");
		if(map==null)
			return EVAL_PAGE;		
		JspWriter out = this.pageContext.getOut();
		StringBuffer buf = new StringBuffer();
		if(_fields!=null){		
		String[] field = StringUtils.split(_fields,'|');
		for(String a : field){
			String value = (String)map.get(a);
			if(value==null)
				continue;
			else
			buf.append("&"+a+"="+value);		
		    }
		}
		try{
			int recNum = NumberUtils.toInt((String)map.get("REC_NUM"));
			int pageNo = NumberUtils.toInt((String)map.get("PAG_NO"));
			int pageCnt = NumberUtils.toInt((String)map.get("PAG_CNT"));
			String pagKey = (String)map.get("PAG_KEY");
			if( pageNo != 1 && pageNo != 0 ) {
				out.println("<a href='"+_txncod+"?REQ_TYP=P&PAG_IDX=050000&PAG_KEY="+pagKey+buf+"&output="+_output+"'"+" onmouseover='setStatusBar('转到首页'); return true'"+">");
			}
			out.println("<img"+" src='img/first-02.gif'"+" alt='首页' border='0' style='margin-top:5px' align='absmiddle'"+"/>");
			if( pageNo != 1 && pageNo != 0 ) {
				out.println("</a>");
			}
		    
			if( pageNo != 1 && pageNo != 0 ) {
			    out.println("<a href='"+_txncod+"?REQ_TYP=P&PAG_IDX=070000&PAG_KEY="+pagKey+buf+"&output="+_output+"'"+" onmouseover='setStatusBar('转到上一页'); return true'"+">");
			}
			out.println("<img"+" src='img/prev-02.gif'"+" alt='上一页' border='0' style='margin-top:5px' align='absmiddle'"+"/>");
			if( pageNo != 1 && pageNo != 0 ) {
			    out.println("</a>");
			}
		    
			if( pageNo != pageCnt ) {
				out.println("<a href='"+_txncod+"?REQ_TYP=P&PAG_IDX=080000&PAG_KEY="+pagKey+buf+"&output="+_output+"'"+" onmouseover='setStatusBar('转到下一页'); return true'"+">");
			}
			
			out.println("<img"+" src='img/next-02.gif'"+" alt='下一页' border='0' style='margin-top:5px' align='absmiddle'"+"/>");
			if( pageNo != pageCnt ) {
			    out.println("</a>");
			}
		    
			if( pageNo != pageCnt ) {
			    out.println("<a href='"+_txncod+"?REQ_TYP=P&PAG_IDX=060000&PAG_KEY="+pagKey + buf+"&output="+_output+"'"+" onmouseover='setStatusBar('转到尾页'); return true'"+">");
			}

			out.println("<img"+" src='img/last-02.gif'"+" alt='尾页' border='0' style='margin-top:5px' align='absmiddle'"+"/>");
			if( pageNo != pageCnt ) {
			    out.println("</a>");
			}
 		    out.println("转到&nbsp;");
			out.println("<input name='pageno' id='pageno' class='INPUT_TEXT' type='text' style='width:24px'/>");
//		    out.println("<a href='javascript:onQryEndPag();' onmouseover='setStatusBar('转到指定页'); return true'"+">");
//		    out.println(" <img name='goPage' src='images/go.gif' boder='0' align='absmiddle'/>");
//		    out.println("</a>");

		    out.println("<span onclick='onQryEndPag()' style='cursor:hand'>");
		    out.println(" <img name='goPage' src='img/go.gif' boder='0' align='absmiddle'/>");
		    out.println("</span>");

		    out.println("<script language='javascript'>");
		    out.println("function onQryEndPag() {");
		    out.println("    alert(onQryEndPag);");
		    out.println("    location.href='"+_txncod+"?REQ_TYP=P&PAG_IDX=00+document.getElementById('pageno').value+&REQ_TYP=P"+buf+"&output="+_output+"';");
		    out.println("    return false;");
			out.println("}");
		    out.println("</script>");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {

		return super.doStartTag();
	}

}

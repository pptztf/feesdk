package com.hisuncmpay.channel.sdk.tag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class URLRewriterTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7846993615480289385L;

	private String value = "";

	// private Logger log = HiLog.getLogger("TAGLIB.trc");

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setHref(String href) {
		this.value = href;
	}

	private static HashMap config = null;

	public synchronized static HashMap loadconfig(File dir) throws IOException,
			DocumentException {
		HashMap result = new HashMap();
		SAXReader reader = new SAXReader();
		File xmlFile = new File(dir, "conf" + File.separator + "WEBCONFIG.XML");
		reader.setValidation(false);
		System.out.println("载入" + xmlFile.getCanonicalPath() + "文件进行URL替换");
		Document document = reader.read(xmlFile);
		if (document != null) {
			List<Element> caseList = document.selectNodes("//req");
			Iterator it = caseList.iterator();
			while (it.hasNext()) {
				Element elt = (Element) it.next();
				
				//System.out.println("req:" + elt.attributeValue("desc"));
				String url = elt.selectSingleNode("xhtml").getText();
				String code = elt.selectSingleNode("dow").getText();
				//System.out.println("xhtml:" + url);
				//System.out.println("dow:" + code);
				if (url != null) {
					url = url.trim();
				}
				if (code != null) {
					code = code.trim();
				}
				result.put(code,url);
			}
		}

		return result;

	}

	public int doStartTag() {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		File file = (File) request.getAttribute("_CONF");
		String caseName = request.getParameter("case");
		String flag = request.getParameter("flush");
		if ((flag != null && flag.length() > 0) || config == null) {
			try {
				config = loadconfig(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (caseName == null) {
			caseName = "";
		}
		JspWriter out = pageContext.getOut();
		// 输入URL的后半部分
		String query = null;
		// 输入URL的前半部分
		String inputuri = null;
		StringBuffer outStr = new StringBuffer();
		if (value.indexOf("?") > 0) {
			query = value.substring(value.indexOf("?"));
			inputuri = value.substring(0,value.indexOf("?"));
		} else {
			inputuri = value;
		}
		String newUrl = null;
		newUrl = (String)config.get(inputuri);
		if (newUrl != null) {
			if (query != null) {
				newUrl = newUrl + query;
			}
			outStr.append(newUrl);
			// outStr.append(newUrl).append("&case="+caseName);
		}
		// 此URL没有在WEBCONFIG中配置
		else {
			
			if (query != null) {
				inputuri = inputuri + query;
			}
			outStr.append(inputuri);
			// outStr.append(inputuri).append("&case="+caseName);
		}
		try {
			out.print(outStr.toString());
		} catch (IOException e) {
			return EVAL_PAGE;
		}
		return EVAL_PAGE;
	}

	public static final boolean isTrueExplicitly(String value) {
		return ((value != null) && (((value.equalsIgnoreCase("true"))
				|| (value.equals("1")) || (value.equalsIgnoreCase("yes")))));
	}

	public static void main(String[] args) throws IOException,
			DocumentException {
		URLRewriterTag rt = new URLRewriterTag();
		File f = new File("D:\\tomcat\\webapps\\ROOT\\data");
		rt.loadconfig(f);
	}
}

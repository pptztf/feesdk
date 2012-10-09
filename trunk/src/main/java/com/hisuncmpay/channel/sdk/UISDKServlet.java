package com.hisuncmpay.channel.sdk;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.hisun.ics.datafmt.json.HiJSONObject;

public class UISDKServlet extends HttpServlet {

	public void init() {

		String config = this.getInitParameter("config");
		String strdebug = this.getInitParameter("debug");
		if (config == null && config.length() < 1) {
			config = System.getenv("UISDK_PATH");
		}

		if (config != null) {

			dir = new File(config + File.separator + "data");
			try {
				System.out.println("����·��Ϊ:" + dir.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(strdebug != null && strdebug.length()>0){
			debug = true;
		}
		
	}
	
	
	private static boolean debug = false;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private final static String CASEKEY = "case";

	private static File dir = null;

	private static HashMap urlmap = null;

	// _LAYOUTMAP
	// _MODULEMAP
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("");
		outInput(request);
		System.out
				.println("#############################################################");
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		response.addHeader("Pragma", "No-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", -10);
		HashMap etf = new HashMap();
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		request.setAttribute("_DEBUG",debug);
		String serviceName = getService(uri, context);
		String caseName = request.getParameter(CASEKEY);
		if (caseName == null || caseName.length() < 1) {
			caseName = "default";
		}
		caseName = new String(caseName.getBytes("ISO-8859-1"), "GBK");
		System.out.println("�������CaseΪ" + caseName);
		System.out.println("�������CaseΪ" + caseName);
		String configpath = null;
		if (urlmap == null || request.getParameter("flush") != null) {
			configpath = loadURLMap();
		}
		String xmlFileName = (String)urlmap.get(serviceName);		
		// ����layout
		HashMap defaultetf = new HashMap();
		loadXML(request);

		if (xmlFileName != null && xmlFileName.length() > 0) {
			SAXReader reader = new SAXReader();
			File xmlFile = null;
			try {
				request.setAttribute("_CONF", dir);
				xmlFile = new File(dir, xmlFileName);
				reader.setValidation(false);
				
				//����SDK mock�ļ�
				Document document = reader.read(xmlFile);
				
				//�ļ���ȡ�ɹ�
				if (document != null) {
					Element root = document.getRootElement();
					
					//����ÿһ����Case�ӵ�
					for (Iterator i_pe = root.elementIterator(); i_pe.hasNext();) {
						Element subelt = (Element) i_pe.next();
						String fathername = subelt.getName();
						//���ӵ��У�����ΪCase
						if(subelt != null && !CASE.equalsIgnoreCase(fathername)){
							
							for (Iterator it = subelt.elementIterator(); it.hasNext();) {
								Element otherelt = (Element) it.next();
								String name = otherelt.getName();
								//����ýӵ�����ı���Ϣ
								if(otherelt.isTextOnly()){
									String value = otherelt.getTextTrim();
									System.out.println("Set Other name["+name+"],value["+value+"]");
									if(SESSION.equalsIgnoreCase(fathername))
										request.getSession(true).setAttribute(name, value);
									else
										request.setAttribute(name, value);
								}
								//������ӽӵ�
								else{
									HashMap map = new HashMap();								
									for (Iterator i_pe1 = otherelt.elementIterator(); i_pe1.hasNext();) {
										Element subothereltelt = (Element) i_pe1.next();
										parserElement(subothereltelt,map);
										System.out.println("Set Other name["+name+"],value size is ["+map.size()+"]");
									}
									if(SESSION.equalsIgnoreCase(fathername)){
										request.getSession(true).setAttribute(name, map);
									}
									else{
										System.out.println("Set gorble name["+name+"]"+map.toString());
										request.setAttribute(name, map);
									}
								}
							}
						}
					}
					
					
//					Element session = (Element)document.selectSingleNode("//Session");
//					if(session != null){
//						for (Iterator it = session.elementIterator(); it.hasNext();) {
//							Element sessionelt = (Element) it.next();
//							String name = sessionelt.getName();
//							if(sessionelt.isTextOnly()){
//								String value = sessionelt.getTextTrim();
//								System.out.println("Set Session name["+name+"],value["+value+"]");
//								request.getSession(true).setAttribute(name, value);
//							}else{
//								HashMap sessionMap = new HashMap();								
//								for (Iterator i_pe = sessionelt.elementIterator(); i_pe.hasNext();) {
//									Element subelt = (Element) i_pe.next();
//									parserElement(subelt,sessionMap);
//									System.out.println("Set Session name["+name+"],value size is ["+sessionMap.size()+"]");
//								}
//								request.getSession(true).setAttribute(name, sessionMap);
//							}
//						}
//					}
//					
//					
//					
//					Element Param = (Element)document.selectSingleNode("//Param");
//					if(Param != null){
//						for (Iterator it = Param.elementIterator(); it.hasNext();) {
//							Element Paramelt = (Element) it.next();
//							String name = Paramelt.getName();
//							if(Paramelt.isTextOnly()){
//								String value = Paramelt.getTextTrim();
//								System.out.println("Set Param name["+name+"],value["+value+"]");
//								request.getSession(true).setAttribute("Param", value);
//							}else{
//								HashMap paramMap = new HashMap();								
//								for (Iterator i_pe = Paramelt.elementIterator(); i_pe.hasNext();) {
//									Element subelt = (Element) i_pe.next();
//									parserElement(subelt,paramMap);
//									System.out.println("Set Param name["+name+"],value size is ["+paramMap.size()+"]");
//								}
//								request.getSession(true).setAttribute("Param", paramMap);
//							}
//						}
//					}
					
					
					List<Element> caseList = document.selectNodes("//Case");
					Iterator it = caseList.iterator();
					boolean falg = false;
					while (it.hasNext()) {
						Element elt = (Element) it.next();
						Attribute attr = elt.attribute("name");
						if (attr != null) {
							if (caseName.equalsIgnoreCase(attr.getValue())) {
								etf = parserETF(elt,request);
								// ��ʾƥ����
								falg = true;
								break;
							}
							if ("default".equalsIgnoreCase(attr.getValue())) {
								defaultetf = parserETF(elt,request);
							}
						}
					}
					// ����Ҳ�����Case����Ĭ��case����
					if (!falg) {
						System.out.println("SDKû���ҵ���صĵ�Case��������default case���");
						etf = defaultetf;
					}
				}
			} catch (DocumentException e) {
				
				System.out.println("�������uriΪ:" + uri);
				System.out.println("���contextΪ:" + context);
				System.out.println("SDK���·����XML�ļ��д���");
				e.printStackTrace();
				response.sendError(500);
				return;
			} finally {
				System.out
						.println("#############################################################");
			}

		}else{
			System.out.println("�������uriΪ:" + uri);
			System.out.println("���xhtmlΪ:" + serviceName);
			System.out.println("SDK��"+configpath+"�����ļ��У�û���ҵ���Ӧ��data�ļ�������)");
			response.sendError(404);
			return;
			
		}
		String rsp_page = (String) etf.get("RSP_PAG");
		request.setAttribute("ETF", etf);
		if (rsp_page == null || rsp_page.length() < 1) {
			Object josnObject = JSONObject.fromObject(etf);
			PrintWriter pw = response.getWriter();
			pw.print(josnObject);
		} else {
			request.getRequestDispatcher(rsp_page).forward(request, response);
		}
	}

	
	
	
	//���뱾��WEBCONFIG�����ļ�
	private String loadURLMap() {
		
		synchronized (this) {
			urlmap = new HashMap();
			SAXReader reader = new SAXReader();
			File xmlFile = null;
			String configpath = null;
			try {

				xmlFile = new File(dir, "conf" + File.separator
						+ "WEBCONFIG.XML");
				reader.setValidation(false);
				System.out.println("��ʼ���뱾��(" + xmlFile.getCanonicalPath()
						+ ")�ļ�����URLMaping");
				configpath = xmlFile.getCanonicalPath();
				Document document = reader.read(xmlFile);
				if (document != null) {
					List<Element> caseList = document.selectNodes("//req");
					Iterator it = caseList.iterator();
					while (it.hasNext()) {
						Element elt = (Element) it.next();

						String xhtml = elt.selectSingleNode("xhtml").getText()
								.trim();
						String data = elt.selectSingleNode("data").getText()
								.trim();
						urlmap.put(xhtml, data);
						
					}					
				}
				System.out.println("URLMaping size is " + urlmap.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configpath;
		}
	}

	
	//�����ݿ����LAYOUT����
	private void loadModule(HttpServletRequest request) {
		HiJSONObject etf = new HiJSONObject();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@211.138.236.209:65152:motion", "portaladm",
					"system2011");
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select LAYOUTFILE,RETURNFILE from ptstlayout");

			int count = 0;

			while (rs.next()) {
				String returnfile = rs.getString("RETURNFILE");
				String layoutfile = rs.getString("LAYOUTFILE");
				etf.put(returnfile, layoutfile);
				count++;
			}
			System.out.println("ͨ����ѯSIT���ݿ�õ���������һ���� " + count);
			request.setAttribute("_LAYOUTMAP", etf);

			HiJSONObject modulmap = new HiJSONObject();
			stmt1 = conn.createStatement();// ����statement
			rs1 = stmt1
					.executeQuery("select MODULEFILE,RETURNFILE from ptstlayout");

			while (rs1.next()) {
				String returnfile = rs1.getString("RETURNFILE");
				String modulefile = rs1.getString("MODULEFILE");
				List module = parModuleFile(modulefile);
				modulmap.put(returnfile, module);
			}
			request.setAttribute("_MODULEMAP", modulmap);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			loadXML(request);
		} finally {
			try {
				rs.close();
				rs1.close();
				stmt.close();
				stmt1.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	//���뱾��LAYOUT�ļ�
	private void loadXML(HttpServletRequest request) {
		SAXReader reader = new SAXReader();

		HiJSONObject etf = new HiJSONObject();
		HiJSONObject modulmap = new HiJSONObject();

		File xmlFile = null;
		try {

			xmlFile = new File(dir, "conf" + File.separator + "LAYOUT.XML");
			reader.setValidation(false);
			System.out.println("�޷�����SIT���ݿ⣬��ʼ���뱾��("
					+ xmlFile.getCanonicalPath() + ")�ļ����в���");

			Document document = reader.read(xmlFile);
			if (document != null) {
				List<Element> caseList = document.selectNodes("//rsp_pag");
				Iterator it = caseList.iterator();
				while (it.hasNext()) {
					Element elt = (Element) it.next();
					Attribute attr = elt.attribute("name");
					String returnFile = attr.getText().trim();
					String layout = elt.selectSingleNode("layout").getText()
							.trim();
					Element modul = (Element)elt.selectSingleNode("module");				
					etf.put(returnFile, layout);
					if(modul != null){
						String modulefile = elt.selectSingleNode("module")
						.getText();
						List module = parModuleFile(modulefile);
						modulmap.put(returnFile, module);
					}
				}
				System.out.println("ͨ�������ļ��õ���������һ���� " + etf.size());
				request.setAttribute("_LAYOUTMAP", etf);
				request.setAttribute("_MODULEMAP", modulmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List parModuleFile(String files) {
		if (files == null || files.length() == 0) {
			return null;
		}
		String[] modules = files.split(";");
		if (modules != null && modules.length > 0) {
			List list = new ArrayList();
			for (int i = 0; i < modules.length; i++) {
				list.add(modules[i]);
			}
			return list;
		}
		return null;
	}
	private static String SESSION="session";
	private static String PARAM="Param";
	private static String CASE="case";
	
	//���������ļ���ETF��������eltΪdocument�ĵ��ӵ�    request���������Ǵ��session
	static private HashMap parserETF(Element elt,HttpServletRequest request) {
		HashMap etf = new HashMap();
		
		for (Iterator i_pe = elt.elementIterator(); i_pe.hasNext();) {
			Element subelt = (Element) i_pe.next();
			
			
//			//����Case�е�session �ڵ�
//			if(SESSION.equalsIgnoreCase(subelt.getName())){
//				for (Iterator it = subelt.elementIterator(); it.hasNext();) {
//					Element sessionelt = (Element) it.next();
//					String name = sessionelt.getName();
//					if(sessionelt.isTextOnly()){
//						String value = sessionelt.getTextTrim();
//						System.out.println("Set Session name["+name+"],value["+value+"]");
//						request.getSession(true).setAttribute(name, value);
//					}else{
//						HashMap sessionMap = new HashMap();								
//						for (Iterator sessinit = sessionelt.elementIterator(); sessinit.hasNext();) {
//							Element subSessionelt = (Element) sessinit.next();
//							parserElement(subSessionelt,sessionMap);
//							System.out.println("Set Session name["+name+"],value size is ["+sessionMap.size()+"]");
//						}
//						request.getSession(true).setAttribute(name, sessionMap);
//					}					
//				}			
//			}else{
			
				//������ͨ�ڵ�
				parserElement(subelt,etf);
//			}
			
			
		}
		if(etf.get("GWA") == null){
			HashMap tmp = new HashMap();
			tmp.put("MSG_INF", "");
			tmp.put("MSG_CD", "DEM00000");
			etf.put("GWA", tmp);
		}

		return etf;
	}
	
	
	//�����ӵ�
	static private void  parserElement(Element elt,HashMap fartherMap){
		if(elt.isTextOnly()){
			String name = elt.getName();
			if(fartherMap.get(name)!= null){				
				parserListElement(elt,fartherMap);
			}else{
				String value = elt.getTextTrim();
				System.out.println("Name:[" + name + "],Value[" + value + "]");
				fartherMap.put(name, value);
			}
		}else{
			String name = elt.getName();
			if(fartherMap.get(name)!= null || REC.equals(name)){				
				parserListElement(elt,fartherMap);
			}else{
				HashMap etf = new HashMap();
				System.out.println("Name:[" + name + "],Value is Map");				
				fartherMap.put(name,etf);				
				for (Iterator i_pe = elt.elementIterator(); i_pe.hasNext();) {
					Element subelt = (Element) i_pe.next();
					parserElement(subelt,etf);
				}
			}
		}		
	}
	private final static String REC="REC";
	
	
	
	//�����ӵ�
	static private void parserListElement(Element elt,HashMap fartherMap){
		String name = elt.getName();
		System.out.println(name);
		Object obj = fartherMap.get(name);
		if(obj instanceof String){
			System.out.println("obj is String");
			if(elt.isTextOnly()){
				List list = new ArrayList();
				list.add(obj);
				list.add(elt.getTextTrim());
				fartherMap.put(name, list);
			}else{
				System.out.println("��������������������������������"+name+"�ڵ����ô���[001]��������������������������������");
			}
		}else if(obj instanceof Map){
			if(elt.isTextOnly()){
				System.out.println("��������������������������������"+name+"�ڵ����ô���[002]��������������������������������");				
			}else{
				List list = new ArrayList();
				list.add(obj);
				HashMap subMap = new HashMap();
				for (Iterator i_pe = elt.elementIterator(); i_pe.hasNext();) {
					Element subelt = (Element) i_pe.next();
					parserElement(subelt,subMap);
				}
				list.add(subMap);
				fartherMap.put(name, list);
			}		
		}else if(obj instanceof List || obj == null){
			List list = new ArrayList();
			if(obj != null)
				list = (List)obj;
			if(elt.isTextOnly()){				
				list.add(elt.getTextTrim());
				fartherMap.put(name, list);
			}else{
				HashMap subMap = new HashMap();
				for (Iterator i_pe = elt.elementIterator(); i_pe.hasNext();) {
					Element subelt = (Element) i_pe.next();
					parserElement(subelt,subMap);
				}
				list.add(subMap);
				fartherMap.put(name, list);
			}
			
		}
	}
	

	static protected String getService(String url, String context) {
		context = context.trim();
		if (context != null && context.length() > 0 && !"/".equals(context)) {
			url = url.substring(context.length(), url.length());
		}
		int idx2 = url.length();
		int idx1 = url.indexOf("/");
		if (idx1 == -1) {
			return null;
		}
		return url.substring(idx1, idx2);
	}
	
	
	//����������
	private void outInput(HttpServletRequest request) throws UnsupportedEncodingException{
		Enumeration en = request.getParameterNames();
		System.out.println("����������==============");
		while (en.hasMoreElements()) {

			String name = (String) en.nextElement();
			name = new String(name.getBytes("ISO-8859-1"), "GBK");
			String[] values = request.getParameterValues(name);
			
			if (values != null) {
				if(values.length ==1){
					values[0] = new String(values[0].getBytes("ISO-8859-1"), "GBK");
					System.out.println("["+name+"]["+values[0]+"]");
				}else{
					StringBuffer sb = new StringBuffer();
					for(String va:values){
						va = new String(va.getBytes("ISO-8859-1"), "GBK");
						sb.append(va).append(",");
					}
					System.out.println("["+name+"]["+sb.toString()+"]");
					
				}
			} 

		}
		System.out.println("����������==============");

	}
	
	
	
	static public void main(String[] args){
		File dir = new File("/Users/Devin/Downloads/100090.XML");
		SAXReader reader = new SAXReader();
		File xmlFile = null;
		try {
			
			reader.setValidation(false);
			//����SDK mock�ļ�
			Document document = reader.read(dir);
			//�ļ���ȡ�ɹ�
			if (document != null) {
				Element root = document.getRootElement();
				
				List<Element> caseList = document.selectNodes("//Case");
				Iterator it = caseList.iterator();
				boolean falg = false;
				while (it.hasNext()) {
					Element elt = (Element) it.next();
					HashMap etf = new HashMap();
					
					for (Iterator i_pe = elt.elementIterator(); i_pe.hasNext();) {
						Element subelt = (Element) i_pe.next();
						parserElement(subelt,etf);
					}
					System.out.println("dddd");
				}
				System.out.println("ssss");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

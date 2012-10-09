package com.hisuncmpay.channel.sdk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.hisun.ics.datafmt.json.HiJSONObject;

public class ToolsServlet extends HttpServlet{
	private static File dir = null;
	public void init() {

		String config = this.getInitParameter("config");

		if (config == null && config.length() < 1) {
			config = System.getenv("UISDK_PATH");
		}

		if (config != null) {

			dir = new File(config + File.separator + "data");
			try {
				System.out.println("Tools配置路径为:" + dir.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(dir);
	}
	
	
	private void process(File confidir){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		File xmlFile = new File(confidir, "conf" + File.separator + "LAYOUT.XML");
		//		 使用 DocumentHelper 类创建一个文档实例
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("LAYOUT");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@211.138.236.209:65152:motion", "portaladm",
					"system2011");
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select LAYOUTFILE,MODULEFILE,RETURNFILE from ptstlayout");

			int count = 0;

			while (rs.next()) {
				Element rsg_pagElement = root.addElement("rsp_pag");
				String returnfile = rs.getString("RETURNFILE");
				rsg_pagElement.addAttribute("name", returnfile);			        
				String layoutfile = rs.getString("LAYOUTFILE");
				Element layoutElement = rsg_pagElement.addElement("layout");
				layoutElement.setText(layoutfile);
				String modulefile = rs.getString("MODULEFILE");
				if(modulefile != null){
					Element moduleElement = rsg_pagElement.addElement("module");
					moduleElement.setText(modulefile);
				}
				count++;
			}
			try {
	            XMLWriter output = new XMLWriter(new FileWriter(xmlFile));
	            output.write(document);
	            output.close();
	            System.out.println("写文件成功" + count);
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }		

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			
		} finally {
			try {
				rs.close();
				
				stmt.close();
				
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		ToolsServlet ts = new ToolsServlet();
		File f = new File("D:\\tomcat\\webapps\\ROOT\\data");
		ts.process(f);
		
	}
}

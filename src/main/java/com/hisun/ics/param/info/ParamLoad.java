package com.hisun.ics.param.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



import net.sf.json.JSONObject;

public class ParamLoad {

//	private void init() throws IOException {
//
//		String jsonStr = BufferedReaderDemo("D:\\tomcat\\webapps\\ROOT\\data\\conf\\sdk.json");
//
//		JSONObject a = JSONObject.fromObject(jsonStr);		
//		System.out.println(a.get("__MENU_PARAM__"));
//		
//		
//	}
//	
//	
//	private void http() throws HttpException, IOException{
//		HttpClient httpclient=new HttpClient();//����һ���ͻ��ˣ����ƴ�һ�������  
//		GetMethod getMethod=new GetMethod("http://172.16.49.232:31011/sdk.jsp");//����һ��get�������������������ַ��������һ����ַ  
//		int statusCode=httpclient.executeMethod(getMethod);//�س�������ȭ��  
//		System.out.println("response=" + getMethod.getResponseBodyAsString());//�쿴ȭͷ������������Ի�õĶ������кܶ࣬����head, cookies�ȵ�  
//		getMethod.releaseConnection();
//		
//	}
//
//	public String BufferedReaderDemo(String path) throws IOException {
//		File file = new File(path);
//		if (!file.exists() || file.isDirectory())
//			throw new FileNotFoundException();
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		String temp = null;
//		StringBuffer sb = new StringBuffer();
//		temp = br.readLine();
//		while (temp != null) {
//			sb.append(temp + " ");
//			temp = br.readLine();
//		}
//		return sb.toString();
//	}
//
//	public static void main(String[] args) throws IOException {
//		String jsonStr = "{\"c\":\"d\",\"a\":\"b\"} ";
//		JSONObject a = JSONObject.fromObject(jsonStr);
//		System.out.println(a); //   
//		System.out.println(a.get("c"));
//		ParamLoad load = new ParamLoad();
//		load.http();
//	}
}

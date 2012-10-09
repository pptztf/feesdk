package com.hisuncmpay.channel.sdk.tag;

import java.io.IOException;
import java.util.Random;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;





public class OcxTag2 extends TagSupport {
	
	private static String UATKEY="30818902818100EA19824037019B6C864394D53A1BC57B0D37A6A8D983C821E9F53B7EDBE5CE339C8BF94C9B50B844BA9AD7CDDCE1372A98637A0264E4BE1F0CFA64584327F3171C9CCFF22D64D4B0C2B988CFFA8F88AE4BC912F9323557858DEFA39C82BD2EFE3F268F21602C7319C87A1336BFA1220FFD60F4CA422A6D2346D5BFB0522F6D050203010001";
	private static String PDTKEY="30818902818100C08A8039099F850481067FA25566FC0CC39C31DBDD0832C4AE0F9B3B4735DDBEB858988D47B9913E5B473ACF66E46F0843430D66AA157B55A2637267BACB659E818A0430888B9836442C782ABE9C422A968C225DA0847450A96D6C9E2ABD37259147CBF8101487ED73D38FF960782F9FB21F1A5CA357BF8B0C3FF64FCCF72AAD0203010001";
		
	private final static String regx="[\\\\s\\\\S]*";
	private final static String regxd ="[\\\\s\\\\S]{6,16}";
	//控件的公钥
	private String publicKey;	
		
	//控件id
	private String ocxid;
	
	//控件的显示模式,默认为*
	private int editType=0;
	
	//控件最大输入长度
	private int maxlength;
	
	
	//检查控件输入中的字符是否满足要求
	private String regularProcessing;
	
	
	//控件输入完毕以后的字符是否满足要求
	private String regularCompleted;
	
	//控件样式
	private String style;
	
	//控件回车键相应事件
	private String eventEnter;
	
	//控件得到焦点事件
	private String eventFocus;
	
	
	//控件失去焦点事件
	private String eventBlur;
	
	
	//火狐控件下tab索引ID
	private String indexID;
	
	//IE控件下tab索引ID
	private String indexNUM;

	//其他代码
	private String other;
	
	
	//引用次数
	private  int times;
	
	
	//直接调用
	private boolean directCall = false;
	
	

	
	
	
	


	
	
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
				StringBuffer outStr = new StringBuffer(100);
		
		//这里将URL改为绝对URL
		outStr.append("http://cmpay.10086.cn/info/ocxobj/PassGuardCtrl.js");
		
		String inputUri = request.getRequestURL().toString().trim();
		inputUri = inputUri.toLowerCase();
		JspWriter out = pageContext.getOut();
		if(inputUri.indexOf("172.16.49.32")!=-1||
				inputUri.indexOf("211.138.236.210")!=-1 ||
					inputUri.indexOf("192.168.1.234")!=-1 || 
					inputUri.indexOf("uatcmpay.10086.cn")!=-1 ||
					inputUri.indexOf("uatipos.10086.cn")!=-1 
			){
			//设定控件的公钥为UAT值
			publicKey=UATKEY;
		}else{
			publicKey=PDTKEY;
		}
		StringBuffer sb = new StringBuffer();
		if(directCall){
			sb.append("IntPassGuardCtrl(\"");
		}else{
			sb.append("<script type=\"text/javascript\">IntPassGuardCtrl(\"");
		}
		
		sb.append(ocxid).append("\",");
		sb.append("\"").append(editType).append("\"").append(",");
		if(indexNUM!=null){
			sb.append("\"").append(indexNUM).append("\",");
		}else{
			sb.append("\"").append("\",");
		}
		if(eventEnter!=null){
			sb.append("\"").append(eventEnter).append("\",");
		}else{
			sb.append("\"").append("\",");
		}
		if(style!=null){
			sb.append("\"").append(style).append("\",");
		}else{
			sb.append("\"").append("\",");
		}
		if(eventFocus!=null){
			sb.append("\"").append(eventFocus).append("\",");
		}else{
			sb.append("\"").append("\",");
		}
		if(eventBlur!=null){
			sb.append("\"").append(eventBlur).append("\")");
		}else{
			sb.append("\"").append("\")");
		}
		sb.append(";");
		if(!directCall){
			sb.append("</script>");
		}		

		StringBuffer sb1 = new StringBuffer();
		if(!directCall){
			sb1.append("<script>");
		}
		sb1.append("SetPassGuardCtrl(\"");
		sb1.append(ocxid).append("\",");
		
		if(maxlength>0){
			sb1.append(maxlength).append(",");
		}else{
			sb1.append(",");
		}
		sb1.append("\"").append("\",");
		if(regularProcessing!=null && !"".equals(regularProcessing)){
			sb1.append("\"").append(regularProcessing).append("\",");
		}else{
			sb1.append("\"").append(regx).append("\",");
		}
		
		if(regularCompleted!=null && !"".equals(regularCompleted)){
			sb1.append("\"").append(regularCompleted).append("\",");
		}else{
			sb1.append("\"").append(regxd).append("\",");
		}
		
		sb1.append("\"").append(publicKey).append("\",");
		
		
		if(indexID!=null){
			sb1.append("\"").append(indexID).append("\",");
		}else{
			sb1.append("\"").append("\",");
		}
		
		if(eventEnter!=null){
			sb1.append("\"").append(eventEnter).append("\")");
		}else{
			sb1.append("\"").append("\")");
		}
		sb1.append(";");
		if(!directCall){
			sb1.append("</script>");
		}		
		try {
			if(times<2){
				if(!directCall)
				out.println("<script type=\"text/javascript\" src=\""+outStr.toString()+"\"></script>");
				
			}
			out.println(sb.toString());
			out.println(sb1.toString());
//			if(!directCall)
//				out.println("<script type=\"text/javascript\">GetPassGuardCtrlStatus(\""+ocxid+"\",\""+num+"\");</script>");
//			else
//				out.println("GetPassGuardCtrlStatus(\""+ocxid+"\",\""+num+"\");");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return EVAL_PAGE;
		}
	}
	
	
	








	public String getOcxid() {
		return ocxid;
	}




	public void setOcxid(String ocxid) {
		this.ocxid = ocxid;
	}




	public String getOther() {
		return other;
	}




	public void setOther(String other) {
		this.other = other;
	}




	

	
	
	
	Random ran = new Random();
	
	private String generateRDNUM(){
		StringBuffer sb = new StringBuffer();		
		for(int i=0;i<32;i++){
			sb.append(ran.nextInt(10));
		}		
		return sb.toString();
	}











	public int getEditType() {
		return editType;
	}











	public void setEditType(int editType) {
		this.editType = editType;
	}











	public String getEventBlur() {
		return eventBlur;
	}











	public void setEventBlur(String eventBlur) {
		this.eventBlur = converInput(eventBlur);
	}











	public String getEventEnter() {
		return eventEnter;
	}











	public void setEventEnter(String eventEnter) {
		this.eventEnter = converInput(eventEnter);
	}











	public String getEventFocus() {
		return eventFocus;
	}











	public void setEventFocus(String eventFocus) {
		this.eventFocus = converInput(eventFocus);
	}











	public String getIndexID() {
		return indexID;
	}











	public void setIndexID(String indexID) {
		this.indexID = indexID;
	}











	public String getIndexNUM() {
		return indexNUM;
	}











	public void setIndexNUM(String indexNUM) {
		this.indexNUM = indexNUM;
	}











	public int getMaxlength() {
		return maxlength;
	}











	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}











	public String getRegularCompleted() {
		return regularCompleted;
	}











	public void setRegularCompleted(String regularCompleted) {
		this.regularCompleted = converInput(regularCompleted);
		
	}











	public String getRegularProcessing() {
		return regularProcessing;
	}











	public void setRegularProcessing(String regularProcessing) {
		this.regularProcessing = converInput(regularProcessing);
	}











	public String getStyle() {
		return style;
	}











	public void setStyle(String style) {
		
		this.style = converInput(style);
	}











	public int getTimes() {
		return times;
	}











	public void setTimes(int times) {
		this.times = times;
	}
	
	
	public static String converInput(String input){
		
		return input.replaceAll("\\\\", "\\\\\\\\");
	}
	
	
	public static void main(String[] args){
		System.out.println(converInput("(\\d{0,16})|(\\d{0,13}\\.\\d{0,2})|(\\d{14}\\.\\d{0,1})|(\\d{15}\\.)"));
	}











	public void setDirectCall(String directCall) {
		this.directCall = "true".equalsIgnoreCase(directCall);
	}











	public boolean getDirectCall() {
		return directCall;
	}











	public void setDirectCall(boolean directCall) {
		this.directCall = directCall;
	}
	
}

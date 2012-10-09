package com.hisuncmpay.channel.sdk.tag;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiFunctions {
	
	/**
     * ֤������ֻ��ʾ�����λ
     * */
    public static String showIDCode(String ID) {
    	return getMaskLastCode(ID,4);
    }
    
    /**
     * �ֻ�����ֻ��ʾǰ����λ
     * */
	public static String showMobileNum(String mobileNum){
		try {
			if (mobileNum != null && mobileNum.length() == 11) {
				String codeStr2 = mobileNum.substring(0, 3);
				for (int i = 0; i < 4; i++) {
					codeStr2 = codeStr2 + "*";
				}
				codeStr2 += mobileNum.substring(7);
				return codeStr2;
			}
			return mobileNum;
		} catch (Exception ex) {
			return mobileNum;
		}
	}
	
	
	
	/**
	 * ��ת���ⲿ��վ��url
	 * @param urlStr��׺��URL
	 * **/
	public static String toServiceUrl(String urlStr){
		String url=""+urlStr;
		
		return url;
	}
	
	/**
	 * ��ת���ⲿ��վ��SSL url
	 * @param urlStr��׺��URL
	 * **/
	public static String toServiceSSLUrl(String urlStr){
		String url=""+urlStr;
		return url;
	}
	
	
	/**
	 * �Զ���ת�����ڸ�ʽ
	 * @param aMask
	 * @param strDate
	 * @return
	 */
	public static String formatDtDIY(String aMask, String strDate) {
		try {
			SimpleDateFormat df = null;
			df = new SimpleDateFormat(aMask);
			Date tempdt;
			String returnValue="";
			if (strDate!= null)
			{
				if (strDate.length()==8)
				{
					tempdt=convertStringToDate("yyyyMMdd", strDate);
				}
				else
				{
					tempdt=convertStringToDate("yyyyMMddHHmmss", strDate);
				}
				returnValue = df.format(tempdt);
			}
			return returnValue;
		} catch (Exception ex) {
			return "����ת������";
		}
	}		
	 public static final Date convertStringToDate(String aMask, String strDate)  throws ParseException {
       SimpleDateFormat df = null;
       Date date = null;
       df = new SimpleDateFormat(aMask);
       date = df.parse(strDate);  
       return (date);
   }
	 
	 
	/**
	 * ��ʽ�����ں�ʱ��
	 * @param aMask
	 * @param strDate
	 * @return
	 */
	public static String formatDt(String strDate) {
		try {
			if (strDate== null)
			{
				return strDate;
			}
			else
			{
				if (strDate.length()!=14)
					return strDate;
				else
				{
					if (strDate.equals("00000000000000"))
					return "0000-00-00 00:00:00";
				}
			}
			SimpleDateFormat df = null;
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempdt;
			String returnValue="";
			tempdt= convertStringToDate("yyyyMMddHHmmss", strDate);
			returnValue = df.format(tempdt);
			return returnValue;
		} catch (Exception ex) {
			return "����ת������";
		}
	}		

	
	/**
	 * ��ʽ�����ڸ�ʽ
	 * @param aMask
	 * @param strDate
	 * @return
	 */
	public static String formatDate(String strDate) {
		try {
			if (strDate == null)
			{
				return strDate;
			}
			else
			{
				if (strDate.length()!=8)
					return strDate;
				else
				{
					if (strDate.equals("00000000"))
					return "0000-00-00";
				}
			}			
			SimpleDateFormat df = null;
			df = new SimpleDateFormat("yyyy-MM-dd");
			Date tempdt;
			String returnValue="";
			tempdt= convertStringToDate("yyyyMMdd", strDate);
			returnValue = df.format(tempdt);
			return returnValue;
		} catch (Exception ex) {
			return "����ת������";
		}
	}	

	
	
	/**
	 * ��ʽ��ʱ��
	 * @param aMask
	 * @param strDate
	 * @return
	 */
	public static String formatTime(String strDate) {
		try {
			if (strDate == null)
			{
				return strDate;
			}
			else
			{
				if (strDate.length()!=6)
					return strDate;
				else
				{
					if (strDate.equals("000000"))
					return "00:00:00";
				}
			}			
			SimpleDateFormat df = null;
			df = new SimpleDateFormat("HH:mm:ss");
			Date tempdt;
			String returnValue="";
			tempdt= convertStringToDate("HHmmss", strDate);
			returnValue = df.format(tempdt);
			return returnValue;
		} catch (Exception ex) {
			return "ʱ��ת������";
		}
	}
	
	
	/**
	 * ��ʾ�ַ������λ��������*�ű�ʾ
	 * @param codeStr Ҫ������ַ���
	 * @param showLen ��ʾ���λ
	 * @return
	 */
	public static String getMaskLastCode(String codeStr,int showLen){
		if (codeStr==null){
			return null;
		}
		//ȥ���ո�
		codeStr = codeStr.trim();
		int len=codeStr.length();
		if (len<showLen) {
			return codeStr;
		}
		String codeStr2=codeStr.substring(len-showLen,len);
		int xj = len-showLen;
		for (int i=0;i<xj;i++){
			codeStr2 ="*"+codeStr2;	
		}
		return codeStr2;
	}
	
	
	public static final String escapeHTMLTags(String content) {
		if (content != null) {
			content = replace(content, "&", "&amp;");
			content = replace(content, "\"", "&quot;");
			content = replace(content, "<", "&lt;");
			content = replace(content, ">", "&gt;");
			return content;
		} else
			return content;
	}

	public static final String unescapeHTMLTags(String content) {
		if (content != null) {
			content = replace(content, "&quot;", "\"");
			content = replace(content, "&lt;", "<");
			content = replace(content, "&gt;", ">");
			content = replace(content, "&amp;", "&");
			return content;
		} else
			return content;
	}
	
	public static final String replace(String line, String oldString, String newString) {
		if (line == null)
			return null;
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j;
			for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
			}

			buf.append(line2, j, line2.length - j);
			return buf.toString();
		} else {
			return line;
		}
	}
	static DecimalFormat df = new DecimalFormat("'��'###,##0.00;'��'-###,##0.00");
	
	public static String formatMoney(String str){
		if(str == null || str.length()<1 ){
			return "0.00";
		}
		try{
			double s = Double.parseDouble(str);
			return  df.format(s);
		}catch(Exception e){
			
			return "��������";
		}
		
	}
	
	public static String getPath(String path){
		return path;
	}
}

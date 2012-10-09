package com.hisuncmpay.channel.sdk.tag;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.hisuncmpay.channel.sdk.tag.expr.Method;






public class HiExpression {
	
	public static String expr(String expr, String arg) throws Exception {
		return expr(expr, new String[]{arg});
	}

	public static String expr2(String expr, String arg0, String arg1) throws Exception {
		return expr(expr, new String[]{arg0, arg1});
	}
	
	public static String expr3(String expr, String arg0, String arg1, String arg2) throws Exception {
		return expr(expr, new String[]{arg0, arg1, arg2});
	}
	
	public static String expr4(String expr, String arg0, String arg1, String arg2, String arg3) throws Exception {
		return expr(expr, new String[]{arg0, arg1, arg2, arg3});
	}
	
	public static String expr5(String expr, String arg0, String arg1, String arg2, String arg3, String arg4) throws Exception {
		return expr(expr, new String[]{arg0, arg1, arg2, arg3, arg4});
	}
	
	static Method basicFunc  = new Method();
	public static String expr(String expr, String[] args) throws Exception {		
		if(StringUtils.isBlank(args[0]) ) {
			return args[0];
		}
		Object[] objectArgs = new Object[2];
		objectArgs[0] = new Object();
		objectArgs[1] = args;
		
		String value="";
		
		try{
			value = (String)MethodUtils.invokeExactMethod(basicFunc, expr, objectArgs);
			return value;
		}catch(NoSuchMethodException e){
			Object exprClass;
			return "NoSuchMethodException";
		}	
		
		
	
		
	}
	
	
}

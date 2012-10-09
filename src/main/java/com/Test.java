package com;

public class Test {
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
	public static void main(String[] args) {
		System.out.println(getService("/OPTSWDC7/PTS2028100.dow", ""));
		System.out.println(System.currentTimeMillis());
	}
}

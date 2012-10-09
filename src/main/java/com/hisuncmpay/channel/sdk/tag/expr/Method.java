package com.hisuncmpay.channel.sdk.tag.expr;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class Method {

	private String[] expres = { "Enc_OCX_PubKey", "Enc_OCX_CLASSID",
			"Enc_OCX_URL", "PASSWORD_FORMAT_NUMBER", "PASSWORD_LENGTH_PAY",
			"OPERTYPE_USER_REG_VALID_SMS", "LOGIN_TYPE_USERPWD",
			"OPERTYPE_USER_GETBACKPWD_VALID_SMS",
			"OPERTYPE_USER_SAVESECURITYPHONE_VALID_SMS" };

	private static HashMap map = new HashMap();
	static {
		map
				.put(
						"Enc_OCX_PubKey",
						"30818902818100C08A8039099F850481067FA25566FC0CC39C31DBDD0832C4AE0F9B3B4735DDBEB858988D47B9913E5B473ACF66E46F0843430D66AA157B55A2637267BACB659E818A0430888B9836442C782ABE9C422A968C225DA0847450A96D6C9E2ABD37259147CBF8101487ED73D38FF960782F9FB21F1A5CA357BF8B0C3FF64FCCF72AAD0203010001");
		map.put("Enc_OCX_CLASSID", "不支持");

		map.put("Enc_OCX_URL", "不支持");
		map.put("PASSWORD_FORMAT_NUMBER", "不支持");
		map.put("PASSWORD_LENGTH_PAY", "不支持");
		map.put("LOGIN_TYPE_USERPWD", "不支持");
		map.put("OPERTYPE_USER_REG_VALID_SMS", "10");
		map.put("OPERTYPE_USER_GETBACKPWD_VALID_SMS", "10");
		map.put("OPERTYPE_USER_SAVESECURITYPHONE_VALID_SMS", "10");

	}
	
	static final int LT = 1;
	  static final int LE = 2;
	  static final int EQ = 3;
	  static final int NE = 4;
	  static final int GT = 5;
	  static final int GE = 6;
	  static final int ER = -1;

	  static int convert(String op)
	  {
	    if ((StringUtils.equals(op, "1")) || (StringUtils.equalsIgnoreCase(op, "lt")))
	    {
	      return 1; }
	    if ((StringUtils.equals(op, "2")) || (StringUtils.equalsIgnoreCase(op, "le")))
	    {
	      return 2; }
	    if ((StringUtils.equals(op, "3")) || (StringUtils.equalsIgnoreCase(op, "eq")))
	    {
	      return 3; }
	    if ((StringUtils.equals(op, "4")) || (StringUtils.equalsIgnoreCase(op, "ne")))
	    {
	      return 4; }
	    if ((StringUtils.equals(op, "5")) || (StringUtils.equalsIgnoreCase(op, "ge")))
	    {
	      return 6; }
	    if ((StringUtils.equals(op, "6")) || (StringUtils.equalsIgnoreCase(op, "gt")))
	    {
	      return 5;
	    }
	    return -1;
	  }

	public static String GETPUBPARA(Object ctx, String[] args) throws Exception {
		if (args.length < 1)
			throw new Exception();
		if (args.length == 2) {
			return "不支持";
		} else if (args.length == 3) {
			return "不支持";
		}
		String str = (String) map.get(args[0]);
		if (str != null) {
			return str;
		} else {
			return "不支持";
		}
	}

	/**
	 * 取得ETF树上加密的数据
	 * 
	 * @param ctx
	 * @param args
	 * @return
	 * @throws HiException
	 */
	public static String GET_DECRYPT_VALUE(Object ctx, String[] args)
			throws Exception {

		String temp = "";

		// 没有参数时写log返回""
		if (args.length < 1) {

			return temp;
			// 只有一个参数时写log并解密
		} else if (args.length == 1) {

			temp = args[0];

			// 有2个参数，第一个是系统名(扩展用),第二个是密文
		} else if (args.length >= 2) {

			temp = args[1];

		}
		return temp;
	}

	public static String AMTFMT(Object ctx, String[] args) throws Exception {
		if (args.length >= 2)
		      throw new Exception("AMTFMT");

		    String amt = args[0].trim();
		    if (!(NumberUtils.isNumber(amt)))
		      throw new Exception("amt is not number"+amt);

		    DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		    if (args.length == 2)
		      fmt = new DecimalFormat(args[2]);

		    return fmt.format(Double.parseDouble(amt));
	}

	public static String FMTSTR(Object ctx, String[] args) throws Exception {
		if (args.length != 2)
		      throw new Exception("215110 FMTSTR");
		    String value = StringUtils.trim(args[0]);
		    String fmt = StringUtils.trim(args[1]);
		    StringBuffer buf = new StringBuffer();
		    int j = 0;
		    for (int i = 0; i < fmt.length(); ++i)
		      if (fmt.charAt(i) == 'n') {
		        if (j >= value.length())
		          break; 
		        buf.append(value.charAt(j));
		        ++j;
		      } else {
		        buf.append(fmt.charAt(i));
		      }

		    return buf.toString();
	}

	public static String FMTDATE(Object ctx, String[] args) throws Exception {
		if (args.length != 3)
		      throw new Exception("FMTDATE");
		    String str = args[0].trim();
		    int type1 = Integer.parseInt(args[1].trim());
		    int type2 = Integer.parseInt(args[2].trim());

		    if ((StringUtils.isEmpty(str)) || (type1 > 6) || (type1 < 0) || (type2 > 6) || (type2 < 0))
		    {
		      throw new Exception("215111 FMTDATEdate|type1{0~6}|type2{0~6}");
		    }

		    String pattern1 = ""; String pattern2 = "";
		    String[] buf1 = { "yyyyMMdd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy年MM月dd日", "yyyy年MM月dd天" };

		    String[] buf2 = { "4Y2M2D", "4Y/2M/2D", "2M/2D/4Y", "4Y.2M.2D", "4Y-2M-2D", "4Y年2M月2D日", "4Y年2M月2D天" };

		    str = str.trim();

		    pattern1 = buf1[type1];
		    pattern2 = buf1[type2];

		    if ((StringUtils.isEmpty(pattern1)) || (StringUtils.isEmpty(pattern2))) {
		      throw new Exception("FMTDATE");
		    }

		    String[] pattern = { pattern1 };
		    try
		    {
		      Date date = DateUtils.parseDate(str, pattern);
		      return DateFormatUtils.format(date, pattern2);
		    } catch (ParseException e) {
		      throw new Exception("215112 FMTDATE");
		    }
	}

	public static String AMTADDDOT(Object ctx, String[] args) throws Exception {
		if (args.length != 1)
		      throw new Exception("215110 AMTADDDOT");

		    String str = args[0].trim();
		    if (!(NumberUtils.isNumber(str))) {
		      throw new Exception("215111 AMTADDDOT str");
		    }

		    StringBuffer buf = new StringBuffer(str);
		    int flag = 1;
		    if (buf.charAt(0) == '-') {
		      flag = -1;
		      buf = buf.delete(0, 1);
		    }
		    if (buf.length() >= 3)
		      buf.insert(buf.length() - 2, '.');
		    else if (buf.length() == 2)
		      buf.insert(0, "0.");
		    else if (buf.length() == 1)
		      buf.insert(0, "0.0");
		    else if (buf.length() == 0)
		      buf.insert(0, '0');

		    if (flag == -1)
		      buf.insert(0, '-');

		    return buf.toString();
	}

	public static String ADDAMT(Object ctx, String[] args) throws Exception {
		if (args.length < 2)
		      throw new Exception("215110 ADDAMT");
		    BigDecimal value = new BigDecimal(args[0].trim());
		    for (int i = 1; i < args.length; ++i) {
		      if (args[i] == null)
		        break;
		      value = value.add(new BigDecimal(args[i].trim()));
		    }
		    return value.toString();
	}

	public static String AMTPOWER(Object ctx, String[] args) throws Exception {
		if (args.length != 2)
		      throw new Exception("215110 AMTPOWER");

		    BigDecimal d = new BigDecimal(args[0].trim());
		    int i = Integer.parseInt(args[1].trim());

		    return String.valueOf(d.movePointRight(i).longValue());
	}
	public static String INTCMP(Object ctx, String[] args) throws Exception {
		int i;
	    if (args.length < 3)
	      throw new Exception("215110");

	    long result = Long.parseLong(StringUtils.trim(args[0]));
	    int op = convert(StringUtils.trim(args[1]));
	    switch (op)
	    {
	    case 1:
	      for (i = 2; i < args.length; ++i)
	        if (result >= Long.parseLong(StringUtils.trim(args[i])))
	          return "0";

	      return "1";
	    case 2:
	      for (i = 2; i < args.length; ++i)
	        if (result > Long.parseLong(StringUtils.trim(args[i])))
	          return "0";

	      return "1";
	    case 3:
	      for (i = 2; i < args.length; ++i)
	        if (result != Long.parseLong(StringUtils.trim(args[i])))
	          return "0";

	      return "1";
	    case 4:
	      for (i = 2; i < args.length; ++i)
	        if (result == Long.parseLong(StringUtils.trim(args[i])))
	          return "0";

	      return "1";
	    case 5:
	      for (i = 2; i < args.length; ++i)
	        if (result <= Long.parseLong(StringUtils.trim(args[i])))
	          return "0";

	      return "1";
	    case 6:
	      for (i = 2; i < args.length; ++i)
	        if (result < Long.parseLong(StringUtils.trim(args[i])))
	          return "0";


	      return "1";
	    }
	    throw new Exception("215111");
	}
	public static String IS_EQUAL_STRING(Object ctx, String[] args)
			throws Exception {
		if (args.length < 2) {
		      throw new Exception("215110 IS_EQUAL_STRING");
		    }

		    for (int i = 0; i < args.length - 1; ++i)
		      if (StringUtils.equals(args[0], args[(i + 1)]))
		        return "1";


		    return "0";
	}

	public static String STRCAT(Object ctx, String[] args) throws Exception {
		if (args.length < 2)
		      throw new Exception("215110 STRCAT");
		    StringBuffer result = new StringBuffer();
		    for (int i = 0; i < args.length; ++i)
		      result.append(args[i]);

		    return result.toString();

	}

	public static String SUBSTR(Object ctx, String[] args) throws Exception {
		if (args.length != 3)
		      throw new Exception("215110 SUBSTR");

		    int beginIndex = Integer.parseInt(StringUtils.trim(args[1])) - 1;
		    byte[] bytes = args[0].getBytes();
		    if (beginIndex < 0)
		      beginIndex = 0;

		    if (beginIndex > bytes.length)
		      beginIndex = bytes.length;

		    int length = Integer.parseInt(StringUtils.trim(args[2]));
		    if (length < 0)
		      length = 0;

		    if (beginIndex + length > bytes.length)
		      length = bytes.length - beginIndex;

		    return new String(bytes, beginIndex, length);
	}
	
	public static String GETDATETIME(Object ctx, String[] args) throws Exception {
		if (args.length != 1)
		      throw new Exception("215110 GETDATETIME");

		    String[] buf1 = { "YYYY", "YY", "MM", "DD", "HH", "MI", "SS" };
		    String[] buf2 = { "yyyy", "yy", "MM", "dd", "HH", "mm", "ss" };
		    String str = args[0].trim();
		    for (int i = 0; i < buf1.length; ++i) {
		      str = StringUtils.replace(str, buf1[i], buf2[i]);
		    }

		    Calendar calendar = Calendar.getInstance();
		    return DateFormatUtils.format(calendar.getTime(), str);
	}
	
}

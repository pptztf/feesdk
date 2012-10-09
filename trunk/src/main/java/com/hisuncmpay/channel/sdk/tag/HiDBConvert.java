package com.hisuncmpay.channel.sdk.tag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;

import com.hisun.ics.datafmt.json.HiJSONObject;

public class HiDBConvert implements HiConvert{
	private static HiDBConvert instance;
	static private String FILED_HELP_PARAM = "__FILED_HELP_PARAM__";
	

	HiDBConvert() {

	}

	public synchronized static HiDBConvert getInstance() {
		if (instance == null) {
			instance = new HiDBConvert();
		}
		return instance;
	}

	public String convert(PageContext pageContext, String name, String value) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@211.138.236.209:65152:motion", "portaladm",
					"system2011");
			stmt = conn.createStatement();			
			rs = stmt
					.executeQuery("select FLD_EXP,FLD_TYP from pubthlp where FLD_NM='"+name+"' and  FLD_VAL='"+value+"'");
			if (rs.next()) {
				String FLD_EXP = rs.getString("FLD_EXP");
				String FLD_TYP = rs.getString("FLD_TYP");
				if (StringUtils.equalsIgnoreCase(FLD_TYP, "i")) {
					HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
					value = StringUtils.replace(value, "${context_root}",
							request.getContextPath());
					return "<img src='" + value + "'/>";
				}
				value = FLD_EXP;
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
		
		
		
		return value;
	}
}

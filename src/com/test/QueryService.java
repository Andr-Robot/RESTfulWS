package com.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Path("QueryService")
public class QueryService {
	
	@GET
	@Path("/alltablename")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 查询所有的表名
	 * @return  [{"Tables_in_mdatabase":"tianyu_operation_week"},{"Tables_in_mdatabase":"tianyu_operation_week_foreign"},{"Tables_in_mdatabase":"tianyu_operation_week_logon_retention"},{"Tables_in_mdatabase":"tianyu_operation_week_new_retention"}]
	 * @throws Exception
	 */
	public String getAllTableName() {
		String gettablenamesql = "show tables";
		DBConnect dbConnect = new DBConnect();
		String result = null;
		try {
			result = dbConnect.query(gettablenamesql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConnect.closeConnect();
		}
		return result;
	}
	
	@POST
	@Path("/data/{receive}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 通过传入的sql查询并返回结果
	 * @param receive
	 * @return
	 */
	public String getData(String receive) {
		DBConnect dbConnect = new DBConnect();
		JSONObject jsonObject = new JSONObject(receive);
		String sql = jsonObject.getString("messages");
		String result = null;
		try {
			result = dbConnect.query(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConnect.closeConnect();
		}
		return result;
	}
}

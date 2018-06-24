package com.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

// 示例文件
//这里@Path定义了类的层次路径。 
//指定了资源类提供服务的URI路径。
@Path("UserInfoService")
public class UserService {
	// @GET表示方法会处理HTTP GET请求
	@GET
	// 这里@Path定义了类的层次路径。指定了资源类提供服务的URI路径。
	@Path("/name/{i}")
	// @Produces定义了资源类方法会生成的媒体类型。
	@Produces(MediaType.APPLICATION_JSON)
	// @PathParam向@Path定义的表达式注入URI参数值。
	public String userName(@PathParam("i") String i) {

		String name = i;
		return "{\"Name\":\"" + name + "\"}" ;
	}

	@GET
	@Path("/age/{j}")
	@Produces(MediaType.APPLICATION_JSON)
	public String userAge(@PathParam("j") int j) {

		int age = j;
		return "{\"Age\":\"" + age + "\"}" ;
	}
	
	@POST
	@Path("/sex/{parm}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.APPLICATION_JSON)
	public String userSex(String parm) {
		JSONObject jsonObject = new JSONObject(parm);
		String sex = jsonObject.getString("mesages");
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("sex", sex);
		JSONObject res = new JSONObject();
		res.put("mesages", jsonObject2);
		return res.toString();
	}
}

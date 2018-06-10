package com.xull.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.TaotaoResult;
import com.xull.common.utils.JsonUtils;
import com.xull.pojo.TbUser;
import com.xull.user.service.UserService;
import com.xull.utils.CookieUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
		TaotaoResult taotaoResult = userService.checkData(param, type);
		return taotaoResult;
	}
	
	@RequestMapping(value = "/register", method =RequestMethod.POST )
	@ResponseBody
	public TaotaoResult register(TbUser user){
		return userService.createUser(user);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		// 1、接收两个参数。
		// 2、调用Service进行登录。
		TaotaoResult result = userService.login(username, password);
		
		if(result.getStatus() == 200){
			// 3、从返回结果中取token，写入cookie。Cookie要跨域。
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		}
		// 4、响应数据。Json数据。TaotaoResult，其中包含Token。
		return result;
	}

	@RequestMapping(value = "/token/{token}", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+token);
		if (StringUtils.isNotEmpty(callback)){
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}
	
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token){
		return userService.logout(token);
	}

}

package com.xull.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xull.common.pojo.TaotaoResult;
import com.xull.pojo.TbUser;
import com.xull.user.service.UserService;
import com.xull.utils.CookieUtils;

@Component
public class UserLoginHandlerInterceptor implements HandlerInterceptor{
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Autowired
	private UserService userService;
	

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		
		System.out.println(">>>>>>>>>>进入用户登录拦截器<<<<<<<<<<<");
		
		if (StringUtils.isEmpty(token)){
			//取当前请求的url
			String url = request.getRequestURL().toString();
			// b)没有token，需要跳转到登录页面。
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" + url);
			//拦截
			return false;
		}
		
		TaotaoResult result = userService.getUserByToken(token);
		TbUser user = (TbUser)result.getData();
		
		if (user == null){
			// d)如果查不到用户信息。用户登录已经过期。需要跳转到登录页面。
			//取当前请求的url
			String url = request.getRequestURL().toString();
			// b)没有token，需要跳转到登录页面。
			response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" + url);
			//拦截
			return false;
		}
		
		request.setAttribute("user", user);
		return true;
	}

}

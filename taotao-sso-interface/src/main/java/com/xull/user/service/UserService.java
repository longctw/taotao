package com.xull.user.service;

import com.xull.common.pojo.TaotaoResult;
import com.xull.pojo.TbUser;

public interface UserService {
	
	/**
	 * 检查参数是否可用
	 * @param param 参数值
	 * @param type 参数类型，username(1),phone(2),email(3)
	 * @return
	 */
	public TaotaoResult checkData(String param, int type);
	
	/**
	 * 创建一个新用户，并保存到数据库
	 * @param user
	 * @return
	 */
	public TaotaoResult createUser(TbUser user);

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public TaotaoResult login(String username, String password);
	
	/**
	 * 根据token值，查询用户是否是登录状态
	 * @param token
	 * @return
	 */
	public TaotaoResult getUserByToken(String token);
	
	/**
	 * 用户退出登录，删除redis中的用户信息
	 * @param token
	 * @return
	 */
	public TaotaoResult logout(String token);
}

package com.hww.learnShiro.learnShiro_chapter02.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm3 implements Realm {

	@Override
	public String getName() {
	
		return "myRealm3";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
	
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String username = (String) token.getPrincipal(); //得到用户名
		//得到密码,不能直接使用 (String) token.getCredentials();
		String password = new String ((char[])token.getCredentials()); 
		
		if(!"zhang".equals(username)) {
			throw new UnknownAccountException(); //用户名错误
		}
		if(!"123".equals(password)) {
			throw new IncorrectCredentialsException(); //密码错误
		}
		
		//如果认证成功，放回一个AuthenticationInfo实现
		return new SimpleAuthenticationInfo(username +"@163.com", password, getName());
	}
	

}

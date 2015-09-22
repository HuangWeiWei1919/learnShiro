package com.hww.learnShiro.learnShiro_chapter02.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class LogInOutTest {

	@Test
	public void testHelloWorld() {

		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro.ini");

		// 得到SecurityManager实例，并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject，及创建用户名/密码 身份验证 token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 登录
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated());

		// 退出
		subject.logout();

		Assert.assertEquals(false, subject.isAuthenticated());

	}

	@Test
	public void testCustomRealm() {

		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-realm.ini");

		// 得到SecurityManager实例，并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject，及创建用户名/密码 身份验证 token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 登录
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated());

		// 退出
		subject.logout();

		Assert.assertEquals(false, subject.isAuthenticated());

	}

	@Test
	public void testCustomMultiRealm() {

		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-multi-realm.ini");

		// 得到SecurityManager实例，并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject，及创建用户名/密码 身份验证 token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 登录
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated());

		// 退出
		subject.logout();

		Assert.assertEquals(false, subject.isAuthenticated());

		UsernamePasswordToken token2 = new UsernamePasswordToken("wang", "321");
		try {
			subject.login(token2);
		} catch (AuthenticationException e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated());

		subject.logout();

		Assert.assertEquals(false, subject.isAuthenticated());
	}

	@Test
	public void testJdbcRealm() {
		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-jdbc-realm.ini");

		// 得到SecurityManager实例，并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject，及创建用户名/密码 身份验证 token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 登录
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated());

		// 退出
		subject.logout();

		Assert.assertEquals(false, subject.isAuthenticated());

	}
	
	
	
	@Test(expected=UnknownAccountException.class)
	public void testAllSuccessfulStrategy() {
		login("classpath:shiro-authenticator-all-success.ini");
		
		Subject subject = SecurityUtils.getSubject();
		
		//得到一个身份集合，其中包含了Realm验证成功的身份信息
		PrincipalCollection collection = subject.getPrincipals();
		Assert.assertEquals(2,collection.asList().size());
	}
	
	@Test
	public void testAllSuccessfulStrategyWithFail() {
		login("classpath:shiro-authenticator-all-fail.ini");
		
		SecurityUtils.getSubject();
		
	
	}
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
	
	/**
	 * 
	 * @Description: 通用登录逻辑
	 * @param configFile
	 * @time: 2015年9月22日 下午5:15:00
	 */
	private void login(String configFile) {
		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		// 得到SecurityManager实例，并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject，及创建用户名/密码 身份验证 token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		
		subject.login(token);
		
		
	}
}

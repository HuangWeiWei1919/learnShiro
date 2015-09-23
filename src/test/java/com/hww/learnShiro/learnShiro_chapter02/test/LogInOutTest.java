package com.hww.learnShiro.learnShiro_chapter02.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class LogInOutTest {
	
	/**
	 * 
	 * @Description: 初始化登录
	 * @time: 2015年9月23日 上午9:41:57
	 */
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
	
	/**
	 * 
	 * @Description: 单个Realm
	 * @time: 2015年9月23日 上午9:43:16
	 */
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
	
	/**
	 * 
	 * @Description: 多个Realm
	 * @time: 2015年9月23日 上午9:45:05
	 */
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
	
	/**
	 * 
	 * @Description: jdbcRealm
	 * @time: 2015年9月23日 上午9:44:47
	 */
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
	
	
	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
}

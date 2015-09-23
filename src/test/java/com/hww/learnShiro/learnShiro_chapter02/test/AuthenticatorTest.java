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

public class AuthenticatorTest {
	/**
	 * 
	 * @Description: 使用AllSuccessfulStrategy，即要满足所有的已配置的Reaml
	 * @time: 2015年9月23日 上午9:45:40
	 */
	@Test
	public void testAllSuccessfulStrategy() {
		login("classpath:shiro-authenticator-all-success.ini");

		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，其中包含了Realm验证成功的身份信息
		PrincipalCollection collection = subject.getPrincipals();
		Assert.assertEquals(2, collection.asList().size());
	}

	/**
	 * 
	 * @Description: 使用AllSuccessfulStrategy，即要满足所有的已配置的Reaml。 这里是失败的情况。
	 * @time: 2015年9月23日 上午9:45:40
	 */
	@Test(expected = UnknownAccountException.class)
	public void testAllSuccessfulStrategyWithFail() {
		login("classpath:shiro-authenticator-all-fail.ini");

		SecurityUtils.getSubject();
	}

	/**
	 * 
	 * @Description: 使用AtLeastOnSuccessfulStrategy策略
	 * @time: 2015年9月23日 上午9:50:37
	 */
	@Test
	public void testAtLeastOneSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-atLeastOne-success.ini");

		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，其中包含了Realm验证成功的身份信息
		PrincipalCollection collection = subject.getPrincipals();
		Assert.assertEquals(2, collection.asList().size());
	}

	/**
	 * 
	 * @Description: 使用FirstSuccessfulStrategy策略，即有满足的Realm就返回
	 * @time: 2015年9月23日 上午10:18:17
	 */
	@Test
	public void testFirstOneSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-firstOne-success.ini");

		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，其中包含了第一个Realm验证成功的身份信息
		PrincipalCollection collection = subject.getPrincipals();
		Assert.assertEquals(1, collection.asList().size());
	}
	
	/**
	 * 
	 * @Description: 自定义策略，只要要满足两个Realm
	 * @time: 2015年9月23日 上午10:56:36
	 */
	@Test
	public void testAtLeastTwoStrategyWithSuccess() {
		login("classpath:shiro-authenticator-atLeastTwo-success.ini");
		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}
	
	/**
	 * 
	 * @Description: 自定义策略，只能有一个Realm满足
	 * @time: 2015年9月23日 上午10:57:21
	 */
	@Test(expected=AuthenticationException.class)
	public void testOnlyOneStrategyWithSuccess() {
		login("classpath:shiro-authenticator-onlyone-success.ini");
		Subject subject = SecurityUtils.getSubject();

		// 得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
		PrincipalCollection principalCollection = subject.getPrincipals();
		
		Assert.assertEquals(1, principalCollection.asList().size());
	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}

	/**
	 * 
	 * @Description: 通用登录逻辑
	 * @param configFile
	 * @time: 2015年9月22日 下午5:15:00
	 */
	private void login(String configFile) {
		// 获取SecurityManager factory,使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				configFile);

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

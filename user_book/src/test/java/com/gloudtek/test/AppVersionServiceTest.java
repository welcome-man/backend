package com.gloudtek.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-renren.xml" })
public class AppVersionServiceTest {

	@Test
	public void test() throws Exception {
		System.out.println("fsdfds");
	}
	
}

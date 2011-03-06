package com.artivisi.ppob.simulator.gateway.pln;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:com/artivisi/**/applicationContext.xml");
		ctx.registerShutdownHook();
	}
}

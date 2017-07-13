package com.hcy.jimweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * ServletComponentScan注解作用是扫描servlet组件
 * @author hcy
 *
 */
@SpringBootApplication
@ServletComponentScan
public class JimWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(JimWebApplication.class, args);
	}
}

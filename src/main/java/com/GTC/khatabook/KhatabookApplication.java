package com.GTC.khatabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

	@SpringBootApplication
	public class KhatabookApplication extends SpringBootServletInitializer {

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(KhatabookApplication.class);
		}

		public static void main(String[] args) {
			SpringApplication.run(KhatabookApplication.class, args);
		}
	}



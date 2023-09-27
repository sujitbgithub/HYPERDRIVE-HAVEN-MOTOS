package com.example.VehicleAppForUSerAndAdmin;

import com.example.VehicleAppForUSerAndAdmin.filter.JwtFilteration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class VehicleAppForUSerAndAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleAppForUSerAndAdminApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilteration> jwtFilter(){
		FilterRegistrationBean<JwtFilteration> registrationBean= new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtFilteration());
		registrationBean.addUrlPatterns("/api/user-vehicle/v1/user/*");
		registrationBean.setName("JwtFilteration");
		registrationBean.setOrder(1);
		return registrationBean;
	}

}

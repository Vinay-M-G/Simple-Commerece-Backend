package com.NightFury.WelcomeService.Proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PRODUCTSERVER")
@Component
public interface ProductServer {
	
	@GetMapping("/api/v1/products/listall")
	public String getProductDetails();
}

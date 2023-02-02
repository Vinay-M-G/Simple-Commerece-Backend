package com.NightFury.UserAndCartService.Proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCTSERVER")
@Component
public interface ProductDetailsProvider {
	
	@GetMapping("/api/v1/products/productview/{productCode}")
	public String getProduct(@PathVariable("productCode") String productCode);
	
	@GetMapping("/api/v1/products/productserviceview/{productCode}")
	public String getProductServices(@PathVariable("productCode") String productCode);
	
	@GetMapping("/api/v1/products/productdeliveryserviceview")
	public String getCartDeliveryServices(@RequestParam("productcodes") String productCodes);
}

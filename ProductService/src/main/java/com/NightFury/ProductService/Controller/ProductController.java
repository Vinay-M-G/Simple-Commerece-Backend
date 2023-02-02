package com.NightFury.ProductService.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NightFury.ProductService.Service.DeliveryAndProductServiceProvider;
import com.NightFury.ProductService.Service.ProductServiceProvider;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
	
	@Autowired
	ProductServiceProvider productServiceProvider;
	
	@Autowired
	DeliveryAndProductServiceProvider deliveryAndProductServiceProvider;
	
	@GetMapping(value = "/listall" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?>  sendAllProducts(){
		Map<String , Object> requestResponse = productServiceProvider.sendAllProducts();
		return ResponseEntity.ok(requestResponse);
	}
	
	@GetMapping(value = "/list" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?>  sendUserRequiredProducts(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "sortby", required = false) String sortby,
			@RequestParam(value = "filterby", required = false) String filterby){
		try {
			List<Map<String, Object>> requestResponse = productServiceProvider.sendCustomisedProducts(category, sortby, filterby);
			return ResponseEntity.ok(requestResponse);	
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getkeyconfigurations" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?>  getKeyConfiguration(){
		try {
			Map<String , Object> response = productServiceProvider.keyConfiguration();
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/productview/{productcode}" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getSingleProductDetail(@PathVariable("productcode") String productCode){
		try {
			Map<String , Object> response = productServiceProvider.sendSingleProductDetail(productCode);
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/productserviceview/{productcode}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getProductServiceView(@PathVariable("productcode") String productCode){
		try {
			List<Map<String, Object>> response = deliveryAndProductServiceProvider.sendProductServices(productCode);
			if(!response.isEmpty()) {
				return ResponseEntity.ok(response);
			}else {
				Map<String , String> emptyResponse = new HashMap<>();
				emptyResponse.put("Warning", "No Services found");
				return ResponseEntity.ok(emptyResponse);
			}
			
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/productdeliveryserviceview", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getProductDeliveryServiceView(@RequestParam("productcodes") String productCode){
		try {
			List<Map<String, Object>> response = deliveryAndProductServiceProvider.sendDeliveryServices(productCode);
			if(!response.isEmpty()) {
				return ResponseEntity.ok(response);
			}else {
				Map<String , String> emptyResponse = new HashMap<>();
				emptyResponse.put("Warning", "No Services found");
				return ResponseEntity.ok(emptyResponse);
			}
			
		}catch(Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
	}
}

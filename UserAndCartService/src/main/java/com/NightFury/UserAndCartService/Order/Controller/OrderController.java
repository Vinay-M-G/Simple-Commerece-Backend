package com.NightFury.UserAndCartService.Order.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NightFury.UserAndCartService.Order.Service.OrderService;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@PostMapping( value = "/createorder/{orderTotal}" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createOrder(@RequestHeader(value = "guid" , required = true) String guid, @PathVariable("orderTotal") double payablePrice, 
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		
		Map<String , Object> response = new HashMap<>();
		
		response = orderService.isValidCartId(guid);
		
		if((boolean) response.get("error")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response = orderService.createOrderAction(guid, payablePrice);
		
		if(!(boolean) response.get("error")) {
			
			supportFunctionsProvider.earseAllCookies(servletRequest, servletResponse);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	
	}
	
	@GetMapping( value = "/getorder/{orderId}" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") String orderId){
		
		Map<String , Object> response = orderService.reteriveOrderDetails(orderId);
		
		if(!(boolean) response.get("error")) {
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	
	}
}

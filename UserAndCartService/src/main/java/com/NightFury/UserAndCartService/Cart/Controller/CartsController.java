package com.NightFury.UserAndCartService.Cart.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NightFury.UserAndCartService.Cart.Service.CartServiceProvider;

@RestController
@RequestMapping("api/v1/carts")
public class CartsController {
	
	@Autowired
	CartServiceProvider cartServiceProvider;
	
	@PostMapping( value = "/addtobasket" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addProductToBasket(@RequestParam(value = "productcode", required = true) String productCode,
				@RequestHeader(value = "guid" , required = true) String guid){
		
		Map<String , Object> response = cartServiceProvider.createProductCartEntry(productCode, guid);
		
		if(!(boolean) response.get("error")) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}
	
	@PatchMapping( value = "/basketserviceupdate" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateProductService(@RequestParam(value = "productcode", required = true) String productCode,
				@RequestParam(value = "servicecode" , required = true) String serviceId,
				@RequestParam(value = "isSelected" , required = true) boolean selected,
				@RequestHeader(value = "guid" , required = true) String guid){
		
		Map<String , Object> response = cartServiceProvider.updateProductServiceEntry(productCode, serviceId, guid, selected);
		
		if(!(boolean) response.get("error")) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}
	
	@PatchMapping( value = "/basketproductupdate" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateProduct(@RequestParam(value = "productcode", required = true) String productCode,
				@RequestParam(value = "quantity" , required = true) int quantity,
				@RequestHeader(value = "guid" , required = true) String guid){
		
		Map<String , Object> response = cartServiceProvider.updateProductQuantity(productCode, guid, quantity);
		
		if(!(boolean) response.get("error")) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}
	
	@GetMapping( value = "/loadbasket" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> sendCartDetails(@RequestHeader(value = "guid", required = true) String guid){
		
		Map<String , Object> response = cartServiceProvider.getCartDetails(guid);
		if(!response.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put("guid", guid);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
	}
	
	@PostMapping( value = "/basketdeliveryserviceupdate" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateDeliveryProductService(@RequestParam(value = "servicecode" , required = true) String serviceId,
				@RequestHeader(value = "guid", required = true) String guid){
		
		Map<String , Object> response = cartServiceProvider.updateDeliveryServiceEntry(serviceId, guid);
		
		if(!(boolean) response.get("error")) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}

}

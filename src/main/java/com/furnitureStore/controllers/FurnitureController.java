package com.furnitureStore.controllers;

import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.furnitureStore.entities.CartResult;
import com.furnitureStore.entities.OrdersResult;
import com.furnitureStore.entities.Result;
import com.furnitureStore.exceptions.InputInvalidException;
import com.furnitureStore.services.FurnitureService;


@Controller
@RequestMapping("/furniture")
public class FurnitureController {
	
	@Autowired
	private FurnitureService furnitureService;
	
	@GetMapping("/products")
	public String getProducts(ModelMap model) {
		Iterable<Result> results = furnitureService.getAll();
		model.addAttribute("results", results);
		return "productsListing";
	}
	
	@GetMapping("/cart")
	public String getCart(ModelMap model, Principal principal) {
		String username =  principal.getName();
		List<CartResult> results = furnitureService.getCartItems(username);
		Double total = furnitureService.getTotal(results);
		model.addAttribute("results", results);
		model.addAttribute("total", total);
		
		return "cart";
	}
	
	@GetMapping("/orders")
	public String getorders(Principal principal, ModelMap model) {
		//Display all carts of the user
		String username =  principal.getName();
		List<OrdersResult> res = furnitureService.findAllOrders(username);
	
		model.addAttribute("res", res);
		return "orders";
	}
	
	@GetMapping("/viewCartItems")
	public String viewCartItems(@RequestParam(value="transactionId") Integer tid, ModelMap model) {
		List<CartResult> results = furnitureService.getCartItems(tid);
		Double total = furnitureService.getTotal(results);
		model.addAttribute("res", results);
		model.addAttribute("total", total);
		return "cartItems";
	}
	
	
	@GetMapping("/paymentInfo")
	public String getPaymentInfo(Principal principal, ModelMap model) {
		String username =  principal.getName();
		List<CartResult> results = furnitureService.getCartItems(username);
		Double total = furnitureService.getTotal(results);
		model.addAttribute("results", results);
		model.addAttribute("total", total);
		model.addAttribute("cid", furnitureService.getCart(username));
		return "detailsForm";
	}
	
	@GetMapping("/aboutProduct")
	public String aboutProduct(@RequestParam(value="productId") Integer id, ModelMap model) {
		Result res = furnitureService.findById(id);
		model.addAttribute("res", res);
		return "aboutProductCustomer";
	}
	
	@GetMapping("/addProductToCart")
	public String addProductToCart(@RequestParam(value="productId") Integer id, ModelMap model,Principal principal) {
		String username = principal.getName();
		System.out.println(username);
		furnitureService.addProductToCart(id, username);
		return "redirect:/music/cart";
	}
	
	@GetMapping("/deleteCartItem")
	public String deleteCartItem(@RequestParam(value="cartId") Integer cid, @RequestParam(value="productId") Integer pid) {
		furnitureService.deleteCartItem(cid, pid);
		return "redirect:/music/cart";
	}
	
	@GetMapping("/deleteCartItems")
	public String deleteCartItems(Principal principal) {
		String username = principal.getName();
		furnitureService.deleteCartItems(username);
		return "redirect:/music/cart";
	}
	
	@PostMapping("/processPayment")
	public String processPayment(HttpServletRequest request, Principal principal) {
		try {
			String username = principal.getName();
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			BigInteger cardNumber = new BigInteger(request.getParameter("cardNumber"));
			Integer cvv = Integer.parseInt(request.getParameter("cvv"));
			LocalDate expiration = LocalDate.parse(request.getParameter("expiration")); 
			String address = request.getParameter("address");
			System.out.println(cid + " " + cardNumber + " " + cvv + " : " + address + " " + expiration);
			
			furnitureService.savePaymentAndCommitTransaction(username, cid, cardNumber, cvv, expiration, address);

		}catch(Exception e) {
			throw new InputInvalidException(e.getMessage());
		}
		
		return "redirect:/music/success";
	}
	
	@GetMapping("/success")
	public String success() {
		return "success";
	}
	
	
}
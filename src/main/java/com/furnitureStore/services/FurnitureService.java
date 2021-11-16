package com.furnitureStore.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furnitureStore.entities.Premium;
import com.furnitureStore.entities.Cart;
import com.furnitureStore.entities.CartResult;
import com.furnitureStore.entities.OrdersResult;
import com.furnitureStore.entities.PaymentInfo;
import com.furnitureStore.entities.Product;
import com.furnitureStore.entities.Result;
import com.furnitureStore.entities.Non_premium;
import com.furnitureStore.entities.Transaction;
import com.furnitureStore.entities.User;
import com.furnitureStore.repositories.PremiumRepository;
import com.furnitureStore.repositories.CartRepository;
import com.furnitureStore.repositories.CategoryRepository;
import com.furnitureStore.repositories.PaymentInfoRepository;
import com.furnitureStore.repositories.ProductRepository;
import com.furnitureStore.repositories.NonPremiumRepository;
import com.furnitureStore.repositories.TransactionRepository;
import com.furnitureStore.repositories.UserRepository;


@Service
public class FurnitureService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private NonPremiumRepository NonPremiumRepository;
	
	@Autowired
	private PremiumRepository PremiumRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PaymentInfoRepository paymentInfoRepository;
	
	public Iterable<Result> getAll() {
		List<Result> results= new ArrayList<>();
		Iterable<Product> products = productRepository.findAll();
		for (Product product : products) {
			//System.out.println(product.getCategory());
			Result res = new Result();
			
			res.setPid(product.getId());
			res.setType(product.getType());
			res.setArtist(product.getArtist());
			res.setPrice(product.getPrice());
			res.setCategory(product.getCategory());
			
			if (product.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(product);
				if (NonPremium != null) {
					res.setTid(NonPremium.getId());
					res.setTitle(NonPremium.getTitle());
					res.setDescription(NonPremium.getDescription());
				}
			}else if (product.getType().equals("Premium")){
				Premium Premium = PremiumRepository.findByProduct(product);
				if (Premium != null) {
//					System.out.println("Premium: " + Premium);
					res.setAid(Premium.getId());
					res.setTitle(Premium.getTitle());
					res.setDescription(Premium.getDescription());
				}
			}
			//System.out.println(res);
			results.add(res);
		}
		return results;
	}

	public Result findById(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			Result res = new Result();
			Product p = product.get();
			res.setPid(p.getId());
			res.setType(p.getType());
			res.setArtist(p.getArtist());
			res.setPrice(p.getPrice());
			res.setCategory(p.getCategory());
			res.setGenre(p.getCategory().getGenre());
			
			if (p.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(p);
				if (NonPremium != null) {
					res.setTid(NonPremium.getId());
					res.setTitle(NonPremium.getTitle());
					res.setDescription(NonPremium.getDescription());
				}
			}else if (p.getType().equals("Premium")){
				Premium Premium = PremiumRepository.findByProduct(p);
				if (Premium != null) {
					res.setAid(Premium.getId());
					res.setTitle(Premium.getTitle());
					res.setDescription(Premium.getDescription());
				}
			}
			
			return res;
		}
		return null;
	}

	public void addProductToCart(Integer id, String username) {
		// Find all carts for the user.
		User user = userRepository.findByUsername(username);
		Iterable<Cart> carts = cartRepository.findByUser(user);
		
		
		// Find the cart with no-transaction 
		Cart cart = null;
		for (Cart c : carts) {
			if (transactionRepository.findByCart(c) == null) {
				cart = c;
				break;
			}
		}
		
		//Else create a new cart
		if (cart == null) {
			cart = new Cart();
			cart.setIsPurchased(false);
			cart.setUser(user);
		}
		
		//Add product to the cart.
		Optional<Product> p = productRepository.findById(id);
		if (p.isPresent()) {
			cart.addProduct(p.get());
			cartRepository.save(cart);
		}
	}

	public List<CartResult> getCartItems(String username) {
		// Find all carts for the user.
		User user = userRepository.findByUsername(username);
		Iterable<Cart> carts = cartRepository.findByUser(user);
		
		
		// Find the cart with no-transaction 
		Cart cart = null;
		for (Cart c : carts) {
			if (transactionRepository.findByCart(c) == null) {
				cart = c;
				break;
			}
		}
		
		//Else create a new cart
		if (cart == null) {
			return new ArrayList<>();
		}
		
		List<Product> products = cart.getProducts();
		List<CartResult> res = new ArrayList<>();
		
		Map<Integer, Integer> map = new HashMap<>();
		for (Product p : products) {
			if (map.containsKey(p.getId())) {
				CartResult cr = res.get(map.get(p.getId()));
				cr.setQuantity(cr.getQuantity()+1);
				cr.setTotalPrice(cr.getPrice() * cr.getQuantity());
				continue;
			}
			
			map.put(p.getId(), res.size());
			CartResult cr = new CartResult();
			cr.setCid(cart.getId());
			cr.setPid(p.getId());
			cr.setPrice(p.getPrice());
			cr.setQuantity(1);
			cr.setTotalPrice(cr.getPrice());
			
			if (p.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(p);
				if (NonPremium != null) {
					cr.setTid(NonPremium.getId());
					cr.setTitle(NonPremium.getTitle());
				}
			}else if (p.getType().equals("Premium")) {
				Premium Premium = PremiumRepository.findByProduct(p);
				if (Premium != null) {
					cr.setAid(Premium.getId());
					cr.setTitle(Premium.getTitle());
				}
			}
			
			res.add(cr);
			
		}
		return res;
	}

	public void deleteCartItem(Integer cid, Integer pid) {
		Optional<Cart> cart = cartRepository.findById(cid);
		if (cart.isPresent()) {
			List<Product> products = cart.get().getProducts();
			Set<Integer> indexes = new HashSet<>();
			//System.out.println(products);
			for (int i = 0; i < products.size(); ++i) {
				if (products.get(i).getId() == pid) {
					indexes.add(i);
				}
			}
			
			List<Product> newProducts = new ArrayList<Product>();
			for (int i = 0; i < products.size(); ++i) {
				if (!indexes.contains(i))
					newProducts.add(products.get(i));
			}
			cart.get().setProducts(newProducts);
			cartRepository.save(cart.get());
		}
	}

	public void deleteCartItems(String username) {
		// Find all carts for the user.
			User user = userRepository.findByUsername(username);
			Iterable<Cart> carts = cartRepository.findByUser(user);
			
			
			// Find the cart with no-transaction 
			Cart cart = null;
			for (Cart c : carts) {
				if (transactionRepository.findByCart(c) == null) {
					cart = c;
					break;
				}
			}
			
		if (cart != null) {
			cart.setProducts(null);
			cartRepository.save(cart);
		}
	}

	public Double getTotal(List<CartResult> results) {
		Double total = 0.0;
		for (CartResult c : results) {
			total += c.getTotalPrice();
		}
		return total;
	}

	public Integer getCart(String username) {
		// Find all carts for the user.
		User user = userRepository.findByUsername(username);
		Iterable<Cart> carts = cartRepository.findByUser(user);
		
		
		// Find the cart with no-transaction 
		Cart cart = null;
		for (Cart c : carts) {
			if (transactionRepository.findByCart(c) == null) {
				cart = c;
				break;
			}
		}
		
		//Else create a new cart
		if (cart == null) {
			return null;
		}
		return cart.getId();
	}

	public void savePaymentAndCommitTransaction(String username, Integer cid, BigInteger cardNumber, Integer cvv,
			LocalDate expiration, String address) {
		User user = userRepository.findByUsername(username);
		PaymentInfo paymentInfo = paymentInfoRepository.findByCardNumberAndCvv(cardNumber, cvv);
		
		if (paymentInfo == null) {
			paymentInfo = new PaymentInfo(cardNumber, expiration, cvv, address);
			paymentInfo.setUser(user);
		}else {
			paymentInfo.setAddress(address);
			paymentInfo.setExpiration(expiration);
			paymentInfo.setUser(user);
		}
		
		paymentInfoRepository.save(paymentInfo);
		
		//Create Transaction
		Optional<Cart> cart = cartRepository.findById(cid);
		if (cart.isPresent()) {
			Transaction transaction = new Transaction();
			transaction.setCart(cart.get());
			transaction.setPurchasedOn(LocalDate.now());
			transaction.setPaymentInfo(paymentInfo);
			transactionRepository.save(transaction);
		}
	}

	public List<OrdersResult> findAllOrders(String username) {
		List<OrdersResult> res = new ArrayList<>();
		
		//get all carts of the user;
		User user = userRepository.findByUsername(username);
		Iterable<Cart> carts = cartRepository.findByUser(user);
		
		for (Cart cart : carts) {
			OrdersResult or = new OrdersResult();
			or.setDelivered(cart.getIsPurchased());
			
			//get transaction info of that user.
			Transaction t = transactionRepository.findByCart(cart);
			if (t != null) {
				or.setPaymentInfo(t.getPaymentInfo());
				or.setTid(t.getId());
				or.setPurchasedOn(t.getPurchasedOn());
				res.add(or);
			}
		}
		return res;
	}

	public List<CartResult> getCartItems(Integer tid) {
		// Find all carts for the user.		
		Optional<Transaction> t = transactionRepository.findById(tid);
		Cart cart = null;
		if (t.isPresent()) {
			cart = t.get().getCart();
		}
		
		//Else create a new cart
		if (cart == null) {
			return new ArrayList<>();
		}
		
		List<Product> products = cart.getProducts();
		List<CartResult> res = new ArrayList<>();
		
		Map<Integer, Integer> map = new HashMap<>();
		for (Product p : products) {
			if (map.containsKey(p.getId())) {
				CartResult cr = res.get(map.get(p.getId()));
				cr.setQuantity(cr.getQuantity()+1);
				cr.setTotalPrice(cr.getPrice() * cr.getQuantity());
				continue;
			}
			
			map.put(p.getId(), res.size());
			CartResult cr = new CartResult();
			cr.setCid(cart.getId());
			cr.setPid(p.getId());
			cr.setPrice(p.getPrice());
			cr.setQuantity(1);
			cr.setTotalPrice(cr.getPrice());
			
			if (p.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(p);
				if (NonPremium != null) {
					cr.setTid(NonPremium.getId());
					cr.setTitle(NonPremium.getTitle());
				}
			}else if (p.getType().equals("Premium")) {
				Premium Premium = PremiumRepository.findByProduct(p);
				if (Premium != null) {
					cr.setAid(Premium.getId());
					cr.setTitle(Premium.getTitle());
				}
			}
			
			res.add(cr);
			
		}
		return res;
	}

	public User changeStatus(Integer tid) {
		// Find all carts for the user.		
		System.out.println(tid + " Change Status");
		Optional<Transaction> t = transactionRepository.findById(tid);
		Cart cart = null;
		if (t.isPresent()) {
			cart = t.get().getCart();
		}
		
	
		cart.setIsPurchased(true);
		cartRepository.save(cart);
		return cart.getUser();
	}
}
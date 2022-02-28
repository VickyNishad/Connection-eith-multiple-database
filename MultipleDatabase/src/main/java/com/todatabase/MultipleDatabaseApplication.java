package com.todatabase;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todatabase.productModel.Product;
import com.todatabase.productRepo.ProductRepo;
import com.todatabase.userModel.User;
import com.todatabase.userRepo.UserRepo;

@SpringBootApplication
@RestController
@ComponentScan(basePackages ={"com.todatabase.*"})
public class MultipleDatabaseApplication {

	@Autowired
	private UserRepo user;
	
	@Autowired
	private ProductRepo product;
	
	@PostConstruct
	public void AddData()
	{
		user.saveAll(Stream.of(new User(101 , "torus" , "torus@mail.com" , 25),
				new User(102 , "Ayush" , "@gmail.com" , 24)).collect(Collectors.toList()));
		product.saveAll(Stream.of(new Product(1 , "mobile" , 12000.00) ,
				new Product(2 , "laptop" , 50000.00)).collect(Collectors.toList()));
		
	}
	
	@GetMapping("/user")
	public List<User> getUsers()
	{
		return user.findAll();
	}
	
	@GetMapping("/product")
	public List<Product> getAlproductList()
	{
		return product.findAll();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MultipleDatabaseApplication.class, args);
	}

}

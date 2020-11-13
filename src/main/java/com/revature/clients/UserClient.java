package com.revature.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.revature.models.User;



@FeignClient(name = "User-Service", url = "http://localhost:8081/user/")
public interface UserClient {

	@PostMapping("/authorize")
	public User authUser(User user);
	
}

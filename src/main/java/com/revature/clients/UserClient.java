package com.revature.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.revature.models.User;



@FeignClient(name = "RevOverflow-UserService")
public interface UserClient {

	@PostMapping("/user/authorize")
	public User authUser(User user);
	
}

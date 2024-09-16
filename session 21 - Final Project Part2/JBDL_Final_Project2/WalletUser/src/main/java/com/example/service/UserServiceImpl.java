package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AppConstants;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl  implements UserSerivice{
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
private UserRepository userRepo;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtService jwtService;

	@Autowired
	ObjectMapper objectMapper;
	@Value("${promotion.user.amount}")
	private float promotionAmt;
	@Override
	public User saveNewUser(User user) {
		if(userRepo.findById(user.getId()).isEmpty()) {
				user.setAmt(user.getAmt()+promotionAmt);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		sendNotification(user.getUserName(), user);
		
		return user;
	}
	@Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }
	@Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
	@Override
	public void sendNotification(String userName, User user) {
		try {
			//Java object to JSON
		String json=	objectMapper.writeValueAsString(user);
		System.out.println(json);
		
		kafkaTemplate.send(AppConstants.NEW_USER,userName,json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//send msg to Kafka
	}

}
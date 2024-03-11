package com.humber.SaffronExpress.services;

import com.humber.SaffronExpress.models.MyUser;
import com.humber.SaffronExpress.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //save user to database

    // 0 - user already exists
    // 1 - user saved successfully
    public int saveUser(MyUser user){
        //check if user exists by username
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return 0;
        }
        //encrypt the password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return 1;
    }
}

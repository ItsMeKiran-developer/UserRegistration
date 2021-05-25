package com.user.registration.service;

import com.user.registration.model.User;
import com.user.registration.model.UserDetails;
import com.user.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public User findByConfirmationToken(String token){
        return userRepo.findByConfirmationToken(token);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(userName);
        if(user == null){
            throw new UsernameNotFoundException("User cannot be found");
        }
        return new UserDetails(user);
    }
}

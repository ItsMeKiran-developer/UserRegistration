package com.user.registration.controller;

import com.user.registration.model.User;
import com.user.registration.service.EmailService;
import com.user.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView processRegistrationForm
            (ModelAndView modelAndView, @Valid User user, BindingResult bindingResult,
             HttpServletRequest request){
        User userFound=userService.findByEmail(user.getEmail());
        if(user !=null){

            modelAndView.addObject("userAlreadyRegisterdMessage",
                    "There is already a user registered with the provided email");
            modelAndView.setViewName("register");
            bindingResult.reject("email") ;
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("register");
        }else{
            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.saveUser(user);

            String appUrl=request.getScheme() + "://" + request.getServerName() + ":8090";

            String message="To set your password, please click on the link below :\n"   +
                    appUrl + "/confirm?token=" + user.getConfirmationToken();

            emailService.sendEmail(user.getEmail(),"Password Settings", message);

            modelAndView.addObject("confirmationMessage",
                    "A password set e-mail has been sent to" + user.getEmail());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @GetMapping("/confirm")
    public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token){

        User user=userService.findByConfirmationToken(token);
        if(user == null){
            modelAndView.addObject("invaliToken","Invalid Confirmation Link");
        }else{
            modelAndView.addObject("confirmationToken",user.getConfirmationToken());
        }
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @PostMapping("/confirm")
    public ModelAndView confirmRegistration
            (ModelAndView modelAndView, BindingResult bindingResult,
             @RequestParam Map<String,String> requestParams ){

        User user= userService.findByConfirmationToken(requestParams.get("token"));

        user.setPassword(passwordEncoder.encode(requestParams.get("password")));
        user.setEnabled(true);
        userService.saveUser(user);

        modelAndView.addObject("successMessage","Password set successfully!");

        modelAndView.setViewName("confirm");

        return modelAndView;
    }
}

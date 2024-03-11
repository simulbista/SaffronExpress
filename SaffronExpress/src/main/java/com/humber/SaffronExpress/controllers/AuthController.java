package com.humber.SaffronExpress.controllers;

import com.humber.SaffronExpress.models.MyUser;
import com.humber.SaffronExpress.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController implements ErrorController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/error")
    public String error403(){
        return "auth/error403";
    }


    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String msg){
        model.addAttribute("msg", msg);
        System.out.println("Hello from login" + msg);
        return "auth/login";
    }

    //custom logout
    @GetMapping("/logout")
    public String customLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        //perform the logout logic
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login?msg=You have been logged out!";
    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(required = false) String msg){
        System.out.println("I am at register get!");
        model.addAttribute("msg", msg);
        model.addAttribute("user", new MyUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute MyUser user){
        System.out.println("I am at register post!" + user.getUsername() + " " + user.getPassword() + " " + user.getRole());
        //save user to database
        int saveUserCode = userService.saveUser(user);
        if(saveUserCode == 0){
            System.out.println("registration failed!");
            return "redirect:/register?msg=User already exists!";
        }else{
            System.out.println("registration success!");
            return "redirect:/login?msg=User registration successful!";
        }
    }

}

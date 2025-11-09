package com.edusmart.controller.ui;

import com.edusmart.dto.JwtResponseDTO;
import com.edusmart.service.ui.UIAuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeUIController {

    @Autowired
    private UIAuthenticationService authenticationService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String mail,
            @RequestParam String password,
            Model model,
            HttpSession session
    ){
        try{
            JwtResponseDTO jwtResponse=authenticationService.login(mail,password);
            session.setAttribute("jwt",jwtResponse.getToken());
            session.setAttribute("userId",jwtResponse.getUserId());
            session.setAttribute("email",jwtResponse.getEmail());
            session.setAttribute("roles",jwtResponse.getRoles());
            return "redirect:/courses";
        }catch (Exception e){
            model.addAttribute("error","Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}

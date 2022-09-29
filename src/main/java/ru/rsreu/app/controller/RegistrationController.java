package ru.rsreu.app.controller;

import ru.rsreu.app.dao.UserRepository;
import ru.rsreu.app.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class RegistrationController {

    private final UserRepository userRepository;


    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/registration")
    public String registration(final Model model) {
        final UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return "registration";
    }

    @PostMapping("/add")
    public String findById(@ModelAttribute UserDto user, Model model) {
        userRepository.save(user.toUser());
        return "redirect:/login";
    }
}

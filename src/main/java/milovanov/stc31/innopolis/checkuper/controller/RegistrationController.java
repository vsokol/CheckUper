package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.User;
import milovanov.stc31.innopolis.checkuper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/registration", "/j_security_check"})
public class RegistrationController {
    UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public ModelAndView registration() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("user", new User());
//        modelAndView.setViewName("registration");
//        return modelAndView;
//    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String renderHost( @ModelAttribute("user") User user ) {
        String s = user.getUsername();
        return s;
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        return "redirect:/";
    }
}

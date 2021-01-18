package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.User;
import milovanov.stc31.innopolis.checkuper.service.IRoleService;
import milovanov.stc31.innopolis.checkuper.service.ISecurityService;
import milovanov.stc31.innopolis.checkuper.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = {"/registration"})
public class RegistrationController {
    ISecurityService securityService;
    IRoleService roleService;
    IUserService userService;

    @Autowired
    public RegistrationController(ISecurityService securityService, IRoleService roleService, IUserService userService) {
        this.securityService = securityService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult
            , @RequestParam(name = "isCustomer", defaultValue = "false") boolean isCustomer
            , @RequestParam(name = "isExecutor", defaultValue = "false") boolean isExecutor
            , Model model) {
        String originalPassword;
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        originalPassword = user.getPassword();
        if (!isCustomer & !isExecutor) {
            model.addAttribute("roleError", "Не задан вид регистрации. Необходимо отметить 'Как заказчик' или 'Как исполнитель'");
            return "registration";
        }
        if (!userService.saveUser(user, isCustomer, isExecutor)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        securityService.autoLogin(user.getUsername(), originalPassword);

        return "redirect:/user/stats";
    }
}

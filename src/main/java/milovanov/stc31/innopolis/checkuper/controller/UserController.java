package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import milovanov.stc31.innopolis.checkuper.service.ISecurityService;
import milovanov.stc31.innopolis.checkuper.service.IUserService;
import milovanov.stc31.innopolis.checkuper.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    ISecurityService securityService;
    IUserService userService;

    @Autowired
    public UserController(IUserService userService, ISecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/stats")
    public String userStats(Model model) {
        // заполняем статистику, устанавливаем как атрибут модели и передаем на страницу
        return "/user/workspase_stats";
    }

    @GetMapping("/setting")
    public String userSetting(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userService.getUserDto(user));
        model.addAttribute("viewAvatar", user.getAvatar());
        model.addAttribute("imgUtil", new ImageUtils());
        if (userService.userIsAdmin(user)) {
            return "/admin/edit_user";
        } else {
            return "/user/setting";
        }
    }

    @PostMapping("/setting")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto
            , BindingResult bindingResult
            , Model model
            , @AuthenticationPrincipal User user
            , @RequestParam("action") String action
            , @RequestParam("passwordNew") String passwordNew
            , @RequestParam("passwordConfirm") String passwordConfirm
            , @RequestParam("avatar") MultipartFile avatar) throws IOException {
        String urlSetting = "/user/setting";

        if ("ChangePassword".equals(action)) {
            // изменение пароля
            if (passwordNew.isEmpty() || (passwordConfirm.isEmpty())) {
                model.addAttribute((passwordNew.isEmpty() ? "passwordNewError" : "passwordConfirmError"), "Пароль не может быть пустым");
                return urlSetting;
            }
            if (!passwordNew.equals(passwordConfirm)) {
                model.addAttribute("passwordError", "Пароли не совпадают");
                return urlSetting;
            }
            if ((passwordNew.length() < 5)
                    || (passwordNew.length() > 256)
                    || (passwordConfirm.length() < 5)
                    || (passwordConfirm.length() > 256)) {
                model.addAttribute("passwordError", "Размер пароля должен находиться в диапазоне от 5 до 256");
                return urlSetting;
            }
            if (!userService.saveNewUserPassword(user, passwordNew)) {
                model.addAttribute("passwordError", "Ошибка при сохранении данных");
                return urlSetting;
            }
            securityService.autoLogin(user.getUsername(), passwordNew);
        } else if ("DeleteAccount".equals(action)) {
            // удаление аккаунта
            userService.delete(user);
            return "redirect:/logout";
        } else {
            // обновление данных пользователя
            if (bindingResult.hasErrors()) {
                return urlSetting;
            }
            if (userDto.getIsCustomer() == null & userDto.getIsExecutor() == null) {
                model.addAttribute("roleError", "Не задана роль. Необходимо отметить 'Заказчик' и/или 'Исполнитель'");
                return urlSetting;
            }
            if (!userDto.getUsername().equals(user.getUsername())
                    && userService.isExistsUser(userDto.getUsername())) {
                model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
                return urlSetting;
            }

            if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
                user.setAvatar(avatar.getBytes());
            }

            if (!userService.saveUser(userDto, user)) {
                model.addAttribute("usernameError", "Ошибка при сохранении данных");
                return urlSetting;
            }
            //securityService.reLogin();
            // TODO: нужно перечитать пользователя
        }
        return "redirect:/user/stats";
    }
}

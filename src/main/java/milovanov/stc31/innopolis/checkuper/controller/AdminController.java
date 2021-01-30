package milovanov.stc31.innopolis.checkuper.controller;

import liquibase.pro.packaged.U;
import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.ResultCheck;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import milovanov.stc31.innopolis.checkuper.service.ISecurityService;
import milovanov.stc31.innopolis.checkuper.service.IUserService;
import milovanov.stc31.innopolis.checkuper.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    ISecurityService securityService;
    IUserService userService;

    @Autowired
    public AdminController(IUserService userService, ISecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUserDto());
        return "/admin/users";
    }

    @GetMapping("/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("viewAvatar", null);
        model.addAttribute("imgUtil", new ImageUtils());
        return "/admin/add_user";
    }

    @PostMapping("/adduser")
    public String saveNewUser(@Valid @ModelAttribute("user") UserDto userDto
            , BindingResult bindingResult
            , Model model
            , @AuthenticationPrincipal User currentUser
            , @RequestParam("action") String action
            , @RequestParam("passwordNew") String passwordNew
            , @RequestParam("passwordConfirm") String passwordConfirm
            , @RequestParam("avatar") MultipartFile avatar) throws IOException {
        String urlSetting = "/admin/add_user";

        if (bindingResult.hasErrors()) {
            return urlSetting;
        }
        User user = new User();
        ResultCheck resultCheck = userService.checkUserBeforeSave(userDto, user);
        if (!resultCheck.getOk()) {
            model.addAttribute(resultCheck.getAttributeName(), resultCheck.getMessage());
            return urlSetting;
        }
        resultCheck = securityService.checkPassword(passwordNew, passwordConfirm);
        if (!resultCheck.getOk()) {
            model.addAttribute(resultCheck.getAttributeName(), resultCheck.getMessage());
            return urlSetting;
        }
        user.setPassword(passwordNew);
        if (!userService.saveUser(userDto, user, true, avatar)) {
            model.addAttribute("usernameError", "Ошибка при сохранении данных");
            return urlSetting;
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/setting")
    public String userSetting(Model model, @AuthenticationPrincipal User currentUser
            , @RequestParam(value = "user_id", required = false) String paramChangeUserId) {

        User user;
        try {
            if (paramChangeUserId != null) {
                Long changeUserId = Long.valueOf(paramChangeUserId);
                User changeUser = userService.findUserById(changeUserId);
                user = (changeUser == null ? currentUser : changeUser);
            } else {
                user = currentUser;
            }
        } catch (NumberFormatException exception) {
            logger.error("Ошибка при разборе параметра user_id = '{}'", paramChangeUserId, exception);
            user = currentUser;
        }

        model.addAttribute("user", userService.getUserDto(user));
        model.addAttribute("viewAvatar", user.getAvatar());
        model.addAttribute("imgUtil", new ImageUtils());
        return "/admin/edit_user";
    }

    @PostMapping("/setting")
    public String updateExistsUser(@Valid @ModelAttribute("user") UserDto userDto
            , BindingResult bindingResult
            , Model model
            , @AuthenticationPrincipal User currentUser
            , @RequestParam("action") String action
            , @RequestParam("passwordNew") String passwordNew
            , @RequestParam("passwordConfirm") String passwordConfirm
            , @RequestParam("avatar") MultipartFile avatar) throws IOException {

        String urlSetting = "/admin/edit_user";
        boolean isLoginUser = userDto.getUserId() == currentUser.getId();
        User user = isLoginUser ? currentUser : userService.findUserById(userDto.getUserId());

        ResultCheck resultCheck;
        if ("ChangePassword".equals(action)) {
            resultCheck = securityService.checkPassword(passwordNew, passwordConfirm);
            if (!resultCheck.getOk()) {
                model.addAttribute(resultCheck.getAttributeName(), resultCheck.getMessage());
                return urlSetting;
            }
            if (!userService.saveNewUserPassword(user, passwordNew)) {
                model.addAttribute("passwordError", "Ошибка при сохранении данных");
                return urlSetting;
            }
            if (isLoginUser) {
                securityService.autoLogin(user.getUsername(), passwordNew);
            }
        }
//        else if ("DeleteAccount".equals(action)) {
//            // удаление аккаунта
//            userService.delete(user);
//            return "redirect:/logout";
//        }
        else {
            // обновление данных пользователя
            if (bindingResult.hasErrors()) {
                return urlSetting;
            }
            resultCheck = userService.checkUserBeforeSave(userDto, user);
            if (!resultCheck.getOk()) {
                model.addAttribute(resultCheck.getAttributeName(), resultCheck.getMessage());
                return urlSetting;
            }

            if (!userService.saveUser(userDto, user, true, avatar)) {
                model.addAttribute("usernameError", "Ошибка при сохранении данных");
                return urlSetting;
            }
            if (isLoginUser) {
                //securityService.reLogin();
                // TODO: нужно перечитать пользователя
            }
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/deleteuser")
    public String deleteUser(Model model, @RequestParam(value = "user_id", required = true) String paramUserId) {
        try {
            userService.delete(userService.findUserById(Long.valueOf(paramUserId)));
        } catch (NumberFormatException exception) {
            logger.error("Ошибка при разборе параметра user_id = '{}'", paramUserId, exception);
        }
        return "redirect:/admin/users";
    }
}

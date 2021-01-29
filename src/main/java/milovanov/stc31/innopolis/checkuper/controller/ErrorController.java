package milovanov.stc31.innopolis.checkuper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/errors"})
public class ErrorController {

    @GetMapping("/403")
    public String error403(Model model) {
        return "/errors/403";
    }
}

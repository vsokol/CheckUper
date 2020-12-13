package milovanov.stc31.innopolis.checkuper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TaskController {

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String getAllTasks(Model model) {
        return "AllCheckTasks";
    }
}

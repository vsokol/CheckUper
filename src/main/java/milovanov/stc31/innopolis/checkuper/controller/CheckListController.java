package milovanov.stc31.innopolis.checkuper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CheckListController {

    @RequestMapping(value = "/checklists", method = RequestMethod.GET)
    public String getAllCheckLists(Model model) {
        return "AllCheckLists";
    }
}

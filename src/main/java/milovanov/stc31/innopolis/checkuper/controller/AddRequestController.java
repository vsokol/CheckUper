package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.dao.RequestDao;
import milovanov.stc31.innopolis.checkuper.dao.TaskDao;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.pojo.RequestStatus;
import milovanov.stc31.innopolis.checkuper.pojo.Task;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddRequestController {
    @Autowired
    private RequestDao requestDao;
    @Autowired
    private TaskDao taskDao;

    @GetMapping(value = "/addrequest")
    public ModelAndView addRequest() {
        Request request = new Request();
        Task task = new Task();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("request", request);
        modelAndView.addObject("task", task);
        modelAndView.setViewName("addrequest");
        return modelAndView;
    }

    @PostMapping(value = "/addrequest")
    public String createRequest(@ModelAttribute Request request, Task task, @AuthenticationPrincipal User user) {
        request.setCustomer(user.getCustomer());
        request.setTaskList(request.getTaskList());
        task.setInfo(task.getInfo());
        task.setRequest(request);
        request.setStatus(RequestStatus.TODO);
        requestDao.save(request);
        taskDao.save(task);
        return "redirect:/user/stats";
    }
}

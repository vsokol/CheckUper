package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExecutorController {
    final private IService<Executor> service;

    @Autowired
    public ExecutorController(IService<Executor> service) {
        this.service = service;
    }

    @GetMapping(value = "/executors")
    public List<Executor> getAllExecutors(Model model) {
        List<Executor> list = service.getAll();
        return list;
    }
}

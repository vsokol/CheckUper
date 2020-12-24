package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RequestController {
    final private IRequestService service;

    @Autowired
    public RequestController(IRequestService service) {
        this.service = service;
    }

    /**
     * Просмотр списка всех заказов
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/requests")
    public ModelAndView getAllRequests() {
        ModelAndView modelAndView =
                getModelAndView(service.getAllRequests(),"Заявки", "allrequests");
        return modelAndView;
    }

    /**
     * Просмотр списка всех доступных для выполнения заказов
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/available_requests")
    public ModelAndView getAllAvailableRequests() {
        ModelAndView modelAndView =
                getModelAndView(service.getAllAvailableRequests(),"Доступные заявки", "allrequests");
        return modelAndView;
    }

    /**
     * Возвращает ModelAndView с установленными необходимыми значениями
     * @param requestList список заявок
     * @param title название закладки
     * @param viewName название html
     * @return ModelAndView с установленными необходимыми значениями
     */
    private ModelAndView getModelAndView(List<Request> requestList, String title, String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", title);
        modelAndView.addObject("requests", requestList);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}

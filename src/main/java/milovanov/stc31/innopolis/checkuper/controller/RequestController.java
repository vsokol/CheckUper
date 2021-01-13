package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.service.IExecutorService;
import milovanov.stc31.innopolis.checkuper.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/requests")
public class RequestController {
    final private IRequestService requestService;
    final private IExecutorService executorService;

    @Autowired
    public RequestController(IRequestService requestService, IExecutorService executorService) {
        this.requestService = requestService;
        this.executorService = executorService;
    }

    /**
     * Просмотр списка всех заказов для указанного исполнителя, если он указан, иначе список всех заказов всех исполнителей.
     * @param paramExecutorId идентификатор исполнителя
     * @return ModelAndView с информацией по заказам и страницей отображения для указанного исполнителя
     */
    @GetMapping
    public ModelAndView getAllRequests(@RequestParam(value = "executor_id", required = false) String paramExecutorId) {
        List<Request> requestList;
        String title;
        try {
            Long executorId = Long.valueOf(paramExecutorId);
            Executor executor = executorService.getExecutorById(executorId);
            if (executor == null) {
                requestList = new ArrayList<>();
                title = "Исполнитель не найден";
            } else {
                requestList = requestService.getAllRequestsByExecutor(executor);
                title = executor.getName() + " (Заявки)";
            }
        } catch (NumberFormatException exception) {
            requestList = requestService.getAllRequests();
            title = "Заявки";
        }
        ModelAndView modelAndView =
                getModelAndView(requestList, title, "allrequests");
        return modelAndView;
    }

    /**
     * Просмотр указанного в <tt>request_id</tt> заказа.
     * @param paramRequestId идентификатор исполнителя
     * @return ModelAndView с информацией по заказу и страницей отображения
     */
    @GetMapping("/show")
    public ModelAndView getRequest(@RequestParam(value = "id", required = false) String paramRequestId) {
        Request request;
        String title;
        try {
            Long requestId = Long.valueOf(paramRequestId);
            request = requestService.getRequestById(requestId);
            if (request == null) {
                title = "Заказ не найден";
            } else {
                title = request.getName();
            }
        } catch (NumberFormatException exception) {
            request = null;
            title = "Заказ не найден";
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", title);
        modelAndView.addObject("request", request);
        modelAndView.setViewName("show_request");
        return modelAndView;
    }

    @GetMapping("/take")
    public ModelAndView takeRequest(@RequestParam(value = "id", required = false) String paramRequestId) {
        Request request;
        String title;
        try {
            Long requestId = Long.valueOf(paramRequestId);
            request = requestService.getRequestById(requestId);
            if (request == null) {
                title = "Заказ не найден";
            } else {
                title = request.getName();
            }
        } catch (NumberFormatException exception) {
            request = null;
            title = "Заказ не найден";
        }
        Executor executor = executorService.getExecutorById(1001L);
        requestService.takeExecutor(request, executor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", title);
        modelAndView.addObject("request", request);

        modelAndView.setViewName("show_request");
        return modelAndView;
    }


    /**
     * Просмотр списка всех доступных для выполнения заказов
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/available")
    public ModelAndView getAllAvailableRequests() {
        ModelAndView modelAndView =
                getModelAndView(requestService.getAllAvailableRequests(),"Доступные заявки", "allrequests");
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

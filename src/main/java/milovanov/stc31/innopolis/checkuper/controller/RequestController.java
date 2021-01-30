package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import milovanov.stc31.innopolis.checkuper.service.IExecutorService;
import milovanov.stc31.innopolis.checkuper.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        String title, viewType;
        try {
            Long executorId = Long.valueOf(paramExecutorId);
            Executor executor = executorService.getExecutorById(executorId);
            if (executor == null) {
                requestList = new ArrayList<>();
                title = "Исполнитель не найден";
                viewType = "all";
            } else {
                requestList = requestService.getAllRequestsByExecutor(executor);
                title = executor.getName() + " (Заявки)";
                viewType = "executor";
            }
        } catch (NumberFormatException exception) {
            requestList = requestService.getAllRequests();
            title = "Заявки";
            viewType = "all";
        }
        ModelAndView modelAndView =
                getModelAndView(requestList
                        , title
                        , viewType
                        , "allrequests");
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
     * Просмотр списка всех заказов, которые я создал
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/my")
    public ModelAndView getRequestsCreatedMe(@AuthenticationPrincipal User user) {
        Customer customer = user.getCustomer();
        ModelAndView modelAndView =
                getModelAndView(requestService.getAllRequestsByCustomer(customer)
                        ,"Мои заявки", "my"
                        , "user/workspase_applications");
        return modelAndView;
    }

    /**
     * Просмотр списка всех доступных для выполнения заказов
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/available")
    public ModelAndView getAllAvailableRequests() {
        ModelAndView modelAndView =
                getModelAndView(requestService.getAllAvailableRequests()
                        ,"Доступные заявки", "available"
                        , "user/workspase_applications_select");
        return modelAndView;
    }

    /**
     * Просмотр списка всех заказов, находящихся у меня в работе
     * @return ModelAndView с информацией по заказам и страницей отображения
     */
    @GetMapping(value = "/progress")
    public ModelAndView getMyRequestsInProgress(@AuthenticationPrincipal User user) {
        Executor executor = user.getExecutor();
        ModelAndView modelAndView =
                getModelAndView(requestService.getAllRequestsByExecutor(executor)
                        ,"Доступные заявки", "progress"
                        , "user/workspase_applications_progress");
        return modelAndView;
    }

    /**
     * Удаление указанного в <tt>request_id</tt> заказа.
     * @param paramRequestId идентификатор удаляемой заявки
     * @return ModelAndView с информацией по заказу и страницей отображения
     */
    @GetMapping("/delete")
    public String deleteRequest(@RequestParam(value = "id", required = false) String paramRequestId) {
        try {
            Long requestId = Long.valueOf(paramRequestId);
            requestService.deleteRequestById(requestId);
        } catch (NumberFormatException exception) {
            return "redirect:/errors/error";
        }
        return "redirect:/requests/my";
    }

    /**
     * Возвращает ModelAndView с установленными необходимыми значениями
     * @param requestList список заявок
     * @param title название закладки
     * @param viewType название элемента меню, который должен быть выбран
     * @param viewName название html
     * @return ModelAndView с установленными необходимыми значениями
     */
    private ModelAndView getModelAndView(List<Request> requestList, String title, String viewType,String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", title);
        modelAndView.addObject("requests", requestList);
        modelAndView.addObject("viewType", viewType);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}

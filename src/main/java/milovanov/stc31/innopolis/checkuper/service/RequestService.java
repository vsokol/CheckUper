package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.RequestDao;
import milovanov.stc31.innopolis.checkuper.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequestService implements IRequestService {
    private RequestDao requestDao;

    @Autowired
    public RequestService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }


    /**
     * Возвращает список всех заказов
     *
     * @return список заказов
     */
    @Override
    public List<Request> getAllRequests() {
        return requestDao.findAll();
    }

    /**
     * Возвращает список завершенных заказов
     * @return список завершенных заказов
     */
    @Override
    public List<Request> getAllClosedRequests() {
        return requestDao.findByStatusIs(RequestStatus.DONE);
    }

    /**
     * Возвращает список всех незавершенных заказов
     *
     * @return список заказов
     */
    @Override
    public List<Request> getAllNotDoneRequests() {
        return requestDao.findByStatusIsNot(RequestStatus.DONE);
    }

    /**
     * Возвращает список всех доступных для выполнения заказов
     *
     * @return список всех доступных для выполнения заказов
     */
    @Override
    public List<Request> getAllAvailableRequests() {
        List<Request> list = requestDao.findByStatusAndExecutorIsNull(RequestStatus.TODO);
        return list;
    }

    /**
     * Возвращает список всех заказов для указанного исполнителя
     *
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    @Override
    public List<Request> getAllRequestsByExecutor(Executor executor) {
        List<Request> list = requestDao.findByExecutor(executor);
        return list;
    }

    /**
     * Возвращает список заказов находящихся в работе для указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    @Override
    public List<Request> getAllRequestsByExecutorInWork(Executor executor) {
        List<Request> list = requestDao.findByExecutorAndStatus(executor, RequestStatus.IN_PROGRESS);
        return list;
    }

    /**
     * Создает новый заказ
     *
     * @param request заказ
     * @return сохраняет новый заказ
     */
    @Override
    public List<Request> createRequest(Request request) {
        return (List<Request>) requestDao.save(request);
    }

    /**
     * Возвращает заказ по указанному идентификтору
     *
     * @param id идентификатор заказа
     * @return заказ
     */
    @Override
    public Request getRequestById(Long id) {
        Request request = requestDao.findRequestById(id);
        return request;
    }

    /**
     * Возвращает список всех заказов указанного заказчика
     * @param customer заказчик
     * @return список всех заказов указанного заказчика
     */
    @Override
    public List<Request> getAllRequestsByCustomer(Customer customer) {
        List<Request> list = requestDao.findByCustomer(customer);
        return list;
    }

    @Override
    public void deleteRequestById(Long id) {
        requestDao.deleteById(id);
    }

    @Override
    public void save(Request request, String stringWithTasks, Customer customer) {
        request.setCustomer(customer);
        request.setStatus(RequestStatus.TODO);
        String[] taskArray = stringWithTasks.split(",");
        for (String info : taskArray) {
            Task task = new Task();
            task.setInfo(info);
            task.setRequest(request);
            if (request.getTaskList() == null) {
                request.setTaskList(new ArrayList<>());
            }
            request.getTaskList().add(task);
        }
        requestDao.save(request);
    }

    /**
     * Взять заявку в работу
     * @param request
     * @param executor
     */
    @Override
    public void takeRequestInWork(Request request, Executor executor) {
        if (request == null || executor == null) {
            return;
        }
        request.setStatus(RequestStatus.IN_PROGRESS);
        request.setExecutor(executor);
        requestDao.save(request);
    }

    /**
     * Завершить исполнение заявки
     * @param request
     */
    @Override
    public void doneRequest(Request request) {
        if (request == null) {
            return;
        }
        request.setStatus(RequestStatus.DONE);
        request.setCompletionTime(new Date());
        requestDao.save(request);
    }
}

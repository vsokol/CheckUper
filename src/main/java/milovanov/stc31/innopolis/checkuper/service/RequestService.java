package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.RequestDao;
import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.pojo.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Request> list = requestDao.findAll();
        return list;
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
    public void takeExecutor(Request request, Executor executor) {

    }
}

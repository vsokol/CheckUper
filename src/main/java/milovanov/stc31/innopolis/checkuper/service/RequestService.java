package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.RequestDao;
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
     * @return список заказов
     */
    @Override
    public List<Request> getAllRequests() {
        List<Request> list = requestDao.findAll();
        return list;
    }

    /**
     * Возвращает список всех доступных для выполнения заказов
     * @return список всех доступных для выполнения заказов
     */
    @Override
    public List<Request> getAllAvailableRequests() {
        List<Request> list = requestDao.findByStatusAndExecutorIsNull(RequestStatus.TODO);
        return list;
    }

    /**
     * Возвращает список всех заказов для указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    @Override
    public List<Request> getAllRequestsByExecutor(Executor executor) {
        List<Request> list = requestDao.findByExecutor(executor);
        return list;
    }
}

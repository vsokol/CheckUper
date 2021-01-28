package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;

import java.util.List;

/**
 * Интерфейс по работе с заказами
 */
public interface IRequestService {

    /**
     * Создает новый заказ
     *
     * @return список заказов
     */
    List<Request> createRequest(Request request);
    /**
     * Возвращает список всех заказов
     * @return список заказов
     */
    List<Request> getAllRequests();

    /**
     * Возвращает список всех доступных для выполнения заказов
     * @return список всех доступных для выполнения заказов
     */
    List<Request> getAllAvailableRequests();

    /**
     * Возвращает список всех заказов для указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    List<Request> getAllRequestsByExecutor(Executor executor);

    /**
     * Возварщает заказ по его идентификтору
     * @param id идентификатор заказа
     * @return заказ
     */
    Request getRequestById(Long id);

    /**
     * Возвращает список всех заказов указанного заказчика
     * @param customer заказчик
     * @return список всех заказов указанного заказчика
     */
    List<Request> getAllRequestsByCustomer(Customer customer);

    void takeExecutor(Request request, Executor executor);
}

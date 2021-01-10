package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;

import java.util.List;

/**
 * Интерфейс по работе с заказами
 */
public interface IRequestService {
    /**
     * Возвращает список всех заказов
     *
     * @return список заказов
     */
    List<Request> getAllRequests();

    /**
     * Возвращает список всех доступных для выполнения заказов
     *
     * @return список всех доступных для выполнения заказов
     */
    List<Request> getAllAvailableRequests();

    /**
     * Возвращает список всех заказов для указанного исполнителя
     *
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    List<Request> getAllRequestsByExecutor(Executor executor);

    /**
     * Создает новый заказ
     *
     * @return список заказов
     */
    List<Request> createRequest(Request request);
}

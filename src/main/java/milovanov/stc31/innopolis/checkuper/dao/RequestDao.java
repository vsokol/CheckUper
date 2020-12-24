package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.pojo.RequestStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO слой для работы с заказами
 */
@Repository
public interface RequestDao extends CrudRepository<Request, Long> {
    /**
     * Возвращает список заказов с указанным статусом, у которых еще не определен исполнитель
     * @param status статус заказов
     * @return список заказов с указанным статусом, у которых еще не определен исполнитель
     */
    List<Request> findByStatusAndExecutorIsNull(RequestStatus status);

    /**
     * Возвращает список всех заказов указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов указанного исполнителя
     */
    List<Request> findByExecutor(Executor executor);
 }

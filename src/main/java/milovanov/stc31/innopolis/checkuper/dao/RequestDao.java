package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.pojo.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO слой для работы с заказами
 */
@Repository
public interface RequestDao extends JpaRepository<Request, Long> {
    /**
     * Возвращает список заказов с указанным статусом, у которых еще не определен исполнитель
     * @param status статус заказов
     * @return список заказов с указанным статусом, у которых еще не определен исполнитель
     */
    List<Request> findByStatusAndExecutorIsNull(RequestStatus status);

    /**
     * Возвращает список заказов, у которых не установлен указанный статус
     * @param status статус заказов
     * @return список заказов с указанным статусом, у которых еще не определен исполнитель
     */
    List<Request> findByStatusIsNot(RequestStatus status);

    /**
     * Возвращает список заказов, у которых установлен указанный статус
     * @param status статус заказов
     * @return список заказов с указанным статусом, у которых еще не определен исполнитель
     */
    List<Request> findByStatusIs(RequestStatus status);

    /**
     * Возвращает список всех заказов указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов указанного исполнителя
     */
    List<Request> findByExecutor(Executor executor);

    /**
     * Возвращает список заказов находящихся в работе для указанного исполнителя
     * @param executor исполнитель
     * @return список всех заказов для указанного исполнителя
     */
    List<Request> findByExecutorAndStatus(Executor executor, RequestStatus requestStatus);

    /**
     * Возвбращает заказ по его уникльному идентификатору
     * @param id идентификатор заказа
     * @return заказ
     */
    Request findRequestById(Long id);

    /**
     * Возвращает список всех заказов указанного заказчика
     * @param customer заказчик
     * @return список всех заказов указанного заказчика
     */
    List<Request> findByCustomer(Customer customer);
 }

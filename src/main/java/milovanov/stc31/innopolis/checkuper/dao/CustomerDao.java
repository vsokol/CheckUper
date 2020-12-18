package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Long> {
}

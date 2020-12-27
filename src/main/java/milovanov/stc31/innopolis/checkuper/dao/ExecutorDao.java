package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutorDao extends JpaRepository<Executor, Long> {

}

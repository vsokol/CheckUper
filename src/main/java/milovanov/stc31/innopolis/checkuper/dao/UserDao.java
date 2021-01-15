package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

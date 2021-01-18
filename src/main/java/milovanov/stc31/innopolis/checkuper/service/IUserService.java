package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.User;

public interface IUserService {
    User findUserById(Long id);
    boolean saveUser(User user, boolean isCustomer, boolean isExecutor);
}

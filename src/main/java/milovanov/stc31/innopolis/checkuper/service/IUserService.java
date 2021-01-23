package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.User;

public interface IUserService {
    User findUserById(Long id);
    boolean isExistsUser(String name);
    boolean saveNewUserPassword(User user, String password);
    boolean saveUser(User user, boolean isCustomer, boolean isExecutor);
    boolean saveUser(UserDto userDto, User user);
    UserDto getUserDto(User user);
    void delete(User user);
}

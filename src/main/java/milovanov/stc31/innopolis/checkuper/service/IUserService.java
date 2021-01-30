package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.ResultCheck;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User findUserById(Long id);
    boolean isExistsUser(String name);
    boolean saveNewUserPassword(User user, String password);
    boolean saveUser(User user, boolean isCustomer, boolean isExecutor);
    boolean saveUser(UserDto userDto, User user);
    boolean saveUser(UserDto userDto, User user, boolean withAdminOption);
    boolean saveUser(UserDto userDto, User user, boolean withAdminOption, MultipartFile avatar) throws IOException;
    UserDto getUserDto(User user);
    List<UserDto> getAllUserDto();
    void delete(User user);

    List<User> getAllUsers();

    boolean userIsAdmin(User user);
    ResultCheck checkUserBeforeSave(UserDto userDto, User user);
}

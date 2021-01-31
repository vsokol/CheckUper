package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.UserDao;
import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class UserService implements IUserService {
    UserDao userDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    IRoleService roleService;
    IServiceUtils serviceUtils;

    @Autowired
    public UserService(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, IRoleService roleService, IServiceUtils serviceUtils) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean isExistsUser(String name) {
        return userDao.findByUsername(name) != null;
    }

    @Transactional
    @Override
    public boolean saveNewUserPassword(User user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDao.save(user);
        return true;
    }

    @Transactional
    @Override
    public boolean saveUser(User user, boolean isCustomer, boolean isExecutor) {
        User findUser = userDao.findByUsername(user.getUsername());
        if (findUser != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (isCustomer) {
            // заказчик
            roles.addAll(roleService.findByName("ROLE_CUSTOMER"));
            user.setCustomer(new Customer(user.getUsername()));
        }
        if (isExecutor) {
            // исполнитель
            roles.addAll(roleService.findByName("ROLE_EXECUTOR"));
            user.setExecutor(new Executor(user.getUsername()));
        }
        user.setRoles(roles);

        if (user.getFullName() == null) {
            user.setFullName(user.getUsername());
        }
        userDao.save(user);
        return true;
    }

    @Override
    public boolean saveUser(UserDto userDto, User user) {
        return saveUser(userDto, user, false);
    }

    /**
     * Обновляет данные по User на основании UserDto
     *
     * @param userDto
     * @param user
     * @return
     */
    @Transactional
    @Override
    public boolean saveUser(UserDto userDto, User user, boolean withAdminOption) {
        if (user.getUsername() == null
                || !user.getUsername().equals(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
        }
        if (user.getFullName() == null
                || !user.getFullName().equals(userDto.getFullName())) {
            user.setFullName(userDto.getFullName());
            if (user.getExecutor() != null) {
                user.getExecutor().setName(userDto.getFullName());
            }
            if (user.getCustomer() != null) {
                user.getCustomer().setName(userDto.getFullName());
            }
        }

        if (user.getId() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        Set<Role> roles = user.getRoles();

        // выравниваем роль Исполнитель
        boolean userIs = (user.getExecutor() != null);
        boolean userDtoIs = (userDto.getIsExecutor() != null);
        if (userIs != userDtoIs) {
            // изменились роль Исполнитель
            if (userIs) {
                // убираем роль Исполнитель
                removeRole(roles, "ROLE_EXECUTOR");
                user.setExecutor(null);
            } else {
                // добавляем роль Исполнитель
                roles.addAll(roleService.findByName("ROLE_EXECUTOR"));
                user.setExecutor(new Executor(user.getFullName()));
            }
        }

        // выравниваем роль Заказчика
        userIs = (user.getCustomer() != null);
        userDtoIs = (userDto.getIsCustomer() != null);
        if (userIs != userDtoIs) {
            // изменились роль Заказчика
            if (userIs) {
                // убираем роль Заказчик
                removeRole(roles, "ROLE_CUSTOMER");
                user.setCustomer(null);
            } else {
                // добавляем роль Заказчик
                roles.addAll(roleService.findByName("ROLE_CUSTOMER"));
                user.setCustomer(new Customer(user.getFullName()));
            }
        }

        // выравниваем роль Админ
        if (withAdminOption) {
            userIs = userIsAdmin(user);
            userDtoIs = (userDto.getIsAdmin() != null);
            if (userIs != userDtoIs) {
                // изменились роль Админа
                if (userIs) {
                    // убираем роль Админа
                    removeRole(roles, "ROLE_ADMIN");
                } else {
                    // добавляем роль Админа
                    roles.addAll(roleService.findByName("ROLE_ADMIN"));
                }
            }
        }

        userDao.saveAndFlush(user);
        return true;
    }

    @Override
    public boolean saveUser(UserDto userDto, User user, boolean withAdminOption, MultipartFile avatar) throws IOException {
        if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
            user.setAvatar(avatar.getBytes());
        }
        if (saveUser(userDto, user, withAdminOption)) {
            serviceUtils.saveImageToFile(avatar, user.getUsername());
            return true;
        } else {
            return false;
        }
    }

    /**
     * На основании объекта User формирует объект класса UserDto
     *
     * @param user объект на основании которого формируется UserDto
     * @return объект класса UserDto
     */
    @Override
    public UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setIsCustomer(user.getCustomer() != null ? "on" : null);
        userDto.setIsExecutor(user.getExecutor() != null ? "on" : null);
        userDto.setIsAdmin(userIsAdmin(user) ? "on" : null);
        return userDto;
    }

    /**
     * Удаляет указанную роль из коллекции ролей
     *
     * @param roles коллекция ролей
     * @param name  название удаляемой роли
     */
    public void removeRole(Set<Role> roles, String name) {
        if (roles == null || roles.isEmpty() || name == null || name.isEmpty()) {
            return;
        }
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (name.equals(role.getName())) {
                iterator.remove();
            }
        }
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public List<UserDto> getAllUserDto() {
        List<UserDto> list = new ArrayList<>();
        List<User> usersList = getAllUsers();
        if (usersList != null & !usersList.isEmpty()) {
            for (User user : usersList) {
                list.add(getUserDto(user));
            }
        }
        return list;
    }

    @Override
    public boolean userIsAdmin(User user) {
        for (Role role : user.getRoles()) {
            if ("ROLE_ADMIN".equals(role.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultCheck checkUserBeforeSave(UserDto userDto, User user) {
        if (userDto.getIsCustomer() == null
                & userDto.getIsExecutor() == null
                & userDto.getIsAdmin() == null) {
            return new ResultCheck(false, "roleError", "Не задана роль. Необходимо отметить 'Заказчик' и/или 'Исполнитель'");
        }
        if (!userDto.getUsername().equals(user.getUsername())
                && isExistsUser(userDto.getUsername())) {
            return new ResultCheck(false, "usernameError", "Пользователь с таким именем уже существует");
        }
        return new ResultCheck(true);
    }
}

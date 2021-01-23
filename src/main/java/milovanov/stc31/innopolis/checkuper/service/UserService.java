package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.UserDao;
import milovanov.stc31.innopolis.checkuper.dto.UserDto;
import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.pojo.Role;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    UserDao userDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    IRoleService roleService;

    @Autowired
    public UserService(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, IRoleService roleService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.get();
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
            roles.addAll(roleService.findByName("CUSTOMER_ROLE"));
            user.setCustomer(new Customer(user.getUsername()));
        }
        if (isExecutor) {
            // исполнитель
            roles.addAll(roleService.findByName("EXECUTOR_ROLE"));
            user.setExecutor(new Executor(user.getUsername()));
        }
        user.setRoles(roles);

        if (user.getFullName() == null) {
            user.setFullName(user.getUsername());
        }
        userDao.save(user);
        return true;
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
    public boolean saveUser(UserDto userDto, User user) {
        if (!user.getUsername().equals(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
        }
        if (!user.getFullName().equals(userDto.getFullName())) {
            user.setFullName(userDto.getFullName());
            if (user.getExecutor() != null) {
                user.getExecutor().setName(userDto.getFullName());
            }
            if (user.getCustomer() != null) {
                user.getCustomer().setName(userDto.getFullName());
            }
        }

        Set<Role> roles = user.getRoles();

        // выравниваем роль Исполнитель
        boolean userIs = (user.getExecutor() != null);
        boolean userDtoIs = (userDto.getIsExecutor() != null);
        if (userIs != userDtoIs) {
            // изменились роль Исполнитель
            if (userIs) {
                // убираем роль Исполнитель
                removeRole(roles, "EXECUTOR_ROLE");
                user.setExecutor(null);
            } else {
                // добавляем роль Исполнитель
                roles.addAll(roleService.findByName("EXECUTOR_ROLE"));
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
                removeRole(roles, "CUSTOMER_ROLE");
                user.setCustomer(null);
            } else {
                // добавляем роль Заказчик
                roles.addAll(roleService.findByName("CUSTOMER_ROLE"));
                user.setCustomer(new Customer(user.getFullName()));
            }
        }

        userDao.saveAndFlush(user);
        return true;
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
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setIsCustomer(user.getCustomer() != null ? "on" : null);
        userDto.setIsExecutor(user.getExecutor() != null ? "on" : null);
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
}

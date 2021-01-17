package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.UserDao;
import milovanov.stc31.innopolis.checkuper.pojo.Role;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public boolean saveUser(User user) {
        User findUser = userDao.findByUsername(user.getUsername());
        if (findUser != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = roleService.findByName("USER_ROLE");
        user.setRoles(roles);
        if (user.getFullName() == null) {
            user.setFullName(user.getUsername());
        }
        userDao.save(user);
        return true;
    }
}

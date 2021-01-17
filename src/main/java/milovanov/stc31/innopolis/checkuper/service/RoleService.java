package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.RoleDao;
import milovanov.stc31.innopolis.checkuper.dao.UserDao;
import milovanov.stc31.innopolis.checkuper.pojo.Role;
import milovanov.stc31.innopolis.checkuper.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService {
    RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> findByName(String roleName) {
        Set<Role> roles = roleDao.findByName(roleName);
        return roles;
    }
}

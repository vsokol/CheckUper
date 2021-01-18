package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Role;

import java.util.Set;

public interface IRoleService {
    Set<Role> findByName(String roleName);
    Role findRoleById(Long id);
}

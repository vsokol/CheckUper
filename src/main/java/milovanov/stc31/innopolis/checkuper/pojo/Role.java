package milovanov.stc31.innopolis.checkuper.pojo;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 15, nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;
    @Column(name = "descr")
    private String descr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}

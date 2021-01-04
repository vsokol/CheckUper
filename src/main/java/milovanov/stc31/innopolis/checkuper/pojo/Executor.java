package milovanov.stc31.innopolis.checkuper.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "executor")
public class Executor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "available")
    private boolean available;
    @Column(name = "descr")
    private String descr;
    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    private List<Request> requestList;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}

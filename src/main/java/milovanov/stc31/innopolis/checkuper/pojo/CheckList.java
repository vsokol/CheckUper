package milovanov.stc31.innopolis.checkuper.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "checklist")
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private Request request;
    @Column(name = "descr")
    private String descr;
    @OneToMany(mappedBy = "checkList", fetch = FetchType.LAZY)
    private List<Task> taskList;

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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}

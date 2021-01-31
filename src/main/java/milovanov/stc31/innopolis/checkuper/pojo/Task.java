package milovanov.stc31.innopolis.checkuper.pojo;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "info", length = 100, nullable = false)
    private String info;
    @Column(name = "is_required", nullable = false)
    private boolean isRequired;
    @Column(name = "index_number")
    private Long indexNumber;
    @Column(name = "descr")
    private String descr;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
    private Request request;
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

//    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
//    private TaskResult taskResult;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Long getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Long indexNumber) {
        this.indexNumber = indexNumber;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    //    public TaskResult getTaskResult() {
//        return taskResult;
//    }
//
//    public void setTaskResult(TaskResult taskResult) {
//        this.taskResult = taskResult;
//    }
}

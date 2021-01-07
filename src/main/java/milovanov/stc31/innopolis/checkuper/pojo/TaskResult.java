package milovanov.stc31.innopolis.checkuper.pojo;

import javax.persistence.*;

@Entity
@Table(name = "task_result")
public class TaskResult {

    @Id
    @Column(name = "task_id")
    private Long id;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Task task;
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

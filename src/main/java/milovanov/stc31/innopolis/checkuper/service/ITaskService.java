package milovanov.stc31.innopolis.checkuper.service;

public interface ITaskService {

    void saveStatusOnTask(Long taskId, Boolean isCompleted);
}

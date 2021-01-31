package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.TaskDao;
import milovanov.stc31.innopolis.checkuper.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {
    TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void saveStatusOnTask(Long taskId, Boolean isCompleted) {
        if (taskId == null) {
            return;
        }
        Task task = taskDao.findById(taskId).get();
        if (task != null) {
            task.setCompleted(isCompleted);
            taskDao.save(task);
        }
    }
}

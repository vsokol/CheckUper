package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.ExecutorDao;
import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutorService implements IExecutorService {
    private ExecutorDao executorDao;

    @Autowired
    public ExecutorService(ExecutorDao executorDao) {
        this.executorDao = executorDao;
    }

    @Override
    public List<Executor> getAllExecutors() {
        List<Executor> list = executorDao.findAll();
        return list;
    }

    @Override
    public Executor getExecutorById(Long id) {
        Optional<Executor> executor = executorDao.findById(id);
        return (executor.isPresent() ? executor.get() : null);
    }

}

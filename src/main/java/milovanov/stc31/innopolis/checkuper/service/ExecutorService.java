package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;
import milovanov.stc31.innopolis.checkuper.dao.CRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutorService implements IService<Executor> {
    private CRUD<Executor> executorCRUD;

    @Autowired
    public ExecutorService(CRUD<Executor> executorCRUD) {
        this.executorCRUD = executorCRUD;
    }

    @Override
    public List<Executor> getAll() {
        List<Executor> list = executorCRUD.getAll();
        return list;
    }
}

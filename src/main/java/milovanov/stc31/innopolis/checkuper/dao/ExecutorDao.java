package milovanov.stc31.innopolis.checkuper.dao;

import milovanov.stc31.innopolis.checkuper.MainObj.Executor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExecutorDao implements CRUD<Executor> {
    @Override
    public List<Executor> getAll() {
        List<Executor> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Executor executor = new Executor();
            executor.setName("Customer-" + i);
            list.add(executor);
        }
        return list;
    }

    @Override
    public void add(Executor obj) {

    }

    @Override
    public void update(Executor obj) {

    }

    @Override
    public Executor getById(Long id) {
        return null;
    }

    @Override
    public void remove(Executor obj) {

    }
}

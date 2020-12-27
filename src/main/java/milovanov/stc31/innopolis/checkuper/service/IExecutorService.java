package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Executor;

import java.util.List;

public interface IExecutorService {
    List<Executor> getAllExecutors();

    Executor getExecutorById(Long id);
}

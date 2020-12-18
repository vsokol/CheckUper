package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.dao.RequestDao;
import milovanov.stc31.innopolis.checkuper.pojo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements IService<Request> {
    private RequestDao requestDao;

    @Autowired
    public RequestService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public List<Request> getAll() {
        List<Request> list = (List<Request>) requestDao.findAll();
        return list;
    }
}

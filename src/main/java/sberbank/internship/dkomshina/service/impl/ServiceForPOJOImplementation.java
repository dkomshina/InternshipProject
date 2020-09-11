package sberbank.internship.dkomshina.service.impl;

import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.model.POJO;
import sberbank.internship.dkomshina.service.ServiceForPOJO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ServiceForPOJOImplementation implements ServiceForPOJO {

    private static final Map<Integer, POJO> REPOSITORY = new HashMap<>();

    //TODO <посмотреть, что такое>
    private static final AtomicInteger ID_HOLDER = new AtomicInteger();

    @Override
    public void create(POJO plainOldJavaObject) {
        int idPOJO = ID_HOLDER.incrementAndGet();
        plainOldJavaObject.setId(idPOJO);
        REPOSITORY.put(idPOJO, plainOldJavaObject);
    }

    @Override
    public List<POJO> readAll() {
        return new ArrayList<>(REPOSITORY.values());
    }

    @Override
    public POJO read(int id) {
        return REPOSITORY.get(id);
    }

    @Override
    public boolean update(POJO plainOldJavaObject, int id) {
        if (REPOSITORY.containsKey(id)) {
            plainOldJavaObject.setId(id);
            REPOSITORY.put(id, plainOldJavaObject);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return REPOSITORY.remove(id) != null;
    }
}

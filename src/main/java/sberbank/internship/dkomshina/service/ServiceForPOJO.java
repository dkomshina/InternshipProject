package sberbank.internship.dkomshina.service;

import sberbank.internship.dkomshina.model.POJO;

import java.util.List;

/**
 * CRUD - create, read, update, delete
 */
public interface ServiceForPOJO {

    /**
     * @param plainOldJavaObject - new pojo
     */
    void create(POJO plainOldJavaObject);

    /**
     * @return - list of POJOs
     */
    List<POJO> readAll();

    /**
     * @param id - POJO's id
     * @return - POJO by it's id
     */
    POJO read(int id);

    /**
     * @param plainOldJavaObject - according to this POJO the data is updated
     * @param id                 - POJO id to update
     * @return - true if data has beet updated
     * - false if not
     */
    boolean update(POJO plainOldJavaObject, int id);

    /**
     * @param id - POJO id to delete
     * @return - true if POJO has been deleted
     * - false if not
     */
    boolean delete(int id);
}

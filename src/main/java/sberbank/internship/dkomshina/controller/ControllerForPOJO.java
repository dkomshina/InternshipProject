package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.POJO;
import sberbank.internship.dkomshina.service.ServiceForPOJO;

import java.util.List;

@RestController
public class ControllerForPOJO {

    private final ServiceForPOJO serviceForPOJO;

    @Autowired
    public ControllerForPOJO(ServiceForPOJO serviceForPOJO) {
        this.serviceForPOJO = serviceForPOJO;
    }

    /*
    ? - wildcard
    responseEntity - класс для возврата ответов, возвращаем с помощью него
                     HTTP статус код
    RequestBody - значение параметра подставлется из тела запроса
    Возвращаем статус 201 Created
     */
    @PostMapping(value = "/pojos")
    public ResponseEntity<?> create(@RequestBody POJO plainOldJavaObject) {
        serviceForPOJO.create(plainOldJavaObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    ResponseEntity<List<POJO>> - возвращется не только HTTP статус,
                                 но и тело ответа - список
    Получем список всех POJO с помощью сервера, если он не пуст вовращаем 200 OK.
    Иначе или если он null возвращаем 404 NOT FOUND
     */
    @GetMapping(value = "/pojos")
    public ResponseEntity<List<POJO>> read() {
        List<POJO> pojoList = serviceForPOJO.readAll();
        if (pojoList != null && !pojoList.isEmpty()) {
            return new ResponseEntity<>(pojoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/pojos/{id}")
    public ResponseEntity<POJO> read(@PathVariable(name = "id") int id) {
        POJO pojo = serviceForPOJO.read(id);
        if (pojo != null) {
            return new ResponseEntity<>(pojo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/pojos/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody POJO planJavaObject) {
        if (serviceForPOJO.update(planJavaObject, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/pojos/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        if (serviceForPOJO.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

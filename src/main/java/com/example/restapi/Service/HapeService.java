package com.example.restapi.Service;

import com.example.restapi.Models.hp;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HapeService {

    hp saveHape(hp hape);
    hp getById(int id);
    ResponseEntity<hp> updateById(int id, hp req);
    void deleteById(int id);
    ResponseEntity<List<hp>> getAllHape();
    hp getPotobyId(int id);
}

package com.example.restapi.Service;

import com.example.restapi.Models.Handphone;
import com.example.restapi.Request.HapeReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HapeService {

    Handphone saveHape(Handphone hape);
    Handphone getById(int id);
    ResponseEntity<Handphone> updateById(int id, Handphone req);
    void deleteById(int id);
    ResponseEntity<List<Handphone>> getAllHape();
    Handphone getPotobyId(int id);
}

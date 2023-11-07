package com.example.restapi.Service;

import com.example.restapi.Models.Handphone;
import com.example.restapi.Request.HapeReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HapeService {

    Handphone saveHape(Handphone hape);
    Handphone saveToDb(Handphone hape);
    Handphone getById(int id);
    Handphone updateById(int id, HapeReq req);
    void deleteById(int id);
    List<Handphone> getAllHape();
}

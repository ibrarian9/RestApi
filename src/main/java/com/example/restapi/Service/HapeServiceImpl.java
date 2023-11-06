package com.example.restapi.Service;

import com.example.restapi.Models.Handphone;
import com.example.restapi.Repositories.HapeRepository;
import com.example.restapi.Request.HapeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HapeServiceImpl implements HapeService {

    @Autowired
    private HapeRepository repo;

    @Override
    public Handphone saveHape(Handphone hape) {
        return this.repo.save(hape);
    }

    @Override
    public Handphone getById(int id) {
        Optional<Handphone> optional = repo.findById(id);
        Handphone handphone;
        if (optional.isPresent()){
            handphone = optional.get();
        } else {
            throw new RuntimeException(" Data Not Found For Id " + id);
        }
        return handphone;
    }

    @Override
    public Handphone updateById(int id, HapeReq req) {
        Handphone hape = repo.findById(id).orElseThrow( ()-> new NoSuchElementException("Hape is Not Found"));
        hape.setNama(req.getNama());
        hape.setMerk(req.getMerk());
        hape.setHarga(req.getHarga());
        hape.setKondisi(req.getKondisi());
        hape.setDeskripsi(req.getDeskripsi());
        hape = repo.save(hape);
        return hape;
    }

    @Override
    public void deleteById(int id) {
        this.repo.deleteById(id);
    }

    @Override
    public List<Handphone> getAllHape() {
        return repo.findAll();
    }


}

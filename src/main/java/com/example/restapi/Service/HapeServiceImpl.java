package com.example.restapi.Service;

import com.example.restapi.Models.Handphone;
import com.example.restapi.Repositories.HapeRepository;
import com.example.restapi.Request.HapeReq;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Handphone> updateById(int id, Handphone req) {
        Handphone hape = repo.findById(id).orElseThrow( ()-> new EntityNotFoundException("Hape is Not Found"));
        hape.setNama(req.getNama());
        hape.setMerk(req.getMerk());
        hape.setHarga(req.getHarga());
        hape.setKondisi(req.getKondisi());
        hape.setDeskripsi(req.getDeskripsi());
        hape = repo.save(hape);
        return ResponseEntity.ok(hape);
    }

    @Override
    public void deleteById(int id) {
        this.repo.deleteById(id);
    }

    @Override
    public ResponseEntity<List<Handphone>> getAllHape() {
        return ResponseEntity.ok(repo.findAll());
    }

    @Override
    public Handphone getPotobyId(int id) {
        Optional<Handphone> optional = repo.findById(id);
        Handphone hape;
        if (optional.isPresent()){
            hape = optional.get();
        } else {
            throw new RuntimeException(" Data Not Found For Id " + id);
        }
        return hape;
    }
}

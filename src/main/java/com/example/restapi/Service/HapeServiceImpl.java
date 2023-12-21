package com.example.restapi.Service;

import com.example.restapi.Models.hp;
import com.example.restapi.Repositories.HapeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HapeServiceImpl implements HapeService {

    @Autowired
    private HapeRepository repo;

    @Override
    public hp saveHape(hp hape) {
        return this.repo.save(hape);
    }

    @Override
    public hp getById(int id) {
        Optional<hp> optional = repo.findById(id);
        hp hp;
        if (optional.isPresent()){
            hp = optional.get();
        } else {
            throw new RuntimeException(" Data Not Found For Id " + id);
        }
        return hp;
    }

    @Override
    public ResponseEntity<hp> updateById(int id, hp req) {
        hp hape = repo.findById(id).orElseThrow( ()-> new EntityNotFoundException("Hape is Not Found"));
        hape.setNama(req.getNama());
        hape.setHarga(req.getHarga());
        hape.setRilis(req.getRilis());
        hape.setDeskripsi(req.getDeskripsi());
        hape = repo.save(hape);
        return ResponseEntity.ok(hape);
    }

    @Override
    public void deleteById(int id) {
        this.repo.deleteById(id);
    }

    @Override
    public ResponseEntity<List<hp>> getAllHape() {
        return ResponseEntity.ok(repo.findAll());
    }

    @Override
    public hp getPotobyId(int id) {
        Optional<hp> optional = repo.findById(id);
        hp hape;
        if (optional.isPresent()){
            hape = optional.get();
        } else {
            throw new RuntimeException(" Data Not Found For Id " + id);
        }
        return hape;
    }
}

package com.example.restapi.Controllers;

import com.example.restapi.FileUploadUtil;
import com.example.restapi.Models.Handphone;
import com.example.restapi.Repositories.HapeRepository;
import com.example.restapi.Request.HapeReq;
import com.example.restapi.Service.HapeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/api/hape")
public class HapeController {

    @Autowired
    private HapeService hapeService;

//  Tambah Data
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewHape(@ModelAttribute("handphone") Handphone hape,
                             @RequestParam("file") MultipartFile file) throws IOException {{
    }
        LocalDate d = LocalDate.now();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        hape.setPublish(Date.valueOf(d));
        hape.setFoto(fileName);
        Handphone saveHape = hapeService.saveHape(hape);
        String uploadDir = "uploads/" + saveHape.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, file);
        return "Berhasil Di Upload";
    }

//    Edit Data By Id
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Handphone> getHape(@PathVariable("id") int id, @RequestBody HapeReq req)  {
        Handphone handphone = hapeService.updateById(id, req);
        return ResponseEntity.ok(handphone);
    }

//    Get Data By Id
    @GetMapping(path = "/edit/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Handphone> editHape(@PathVariable("id") int id){
         var data = hapeService.getById(id);
         return ResponseEntity.ok(data);
    }

//    Tampil Senua data
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Handphone>> semuaData(){
        List<Handphone> data = hapeService.getAllHape();
        return ResponseEntity.ok(data);
    }

//    Delete Data By Id
    @DeleteMapping("hapus/{id}")
    public String hapusData(@PathVariable int id){
        this.hapeService.deleteById(id);
        return "Delete Berhasil";
    }
}

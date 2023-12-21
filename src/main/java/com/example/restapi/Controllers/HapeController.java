package com.example.restapi.Controllers;

import com.example.restapi.FileUploadUtil;
import com.example.restapi.Models.hp;
import com.example.restapi.Response.ErrorRes;
import com.example.restapi.Service.HapeService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowedHeaders = "/**")
@RestController
@RequestMapping("/api/hape")
public class HapeController {

    @Autowired
    private HapeService hapeService;

    String path = "src/main/resources/";

//  Tambah Data
    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewHape(@ModelAttribute("handphone") hp hape,
                             @RequestPart("file") MultipartFile file) {{
        }
        try {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileName = StringUtils.cleanPath(originalFilename);
            hape.setFoto(fileName);
            hp saveHape = hapeService.saveHape(hape);
            String uploadDir = path + "uploads/" + saveHape.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            return ResponseEntity.ok("Data Berhasil Di Tambahkan");
        } catch (AccessDeniedException e) {
            ErrorRes res = new ErrorRes(HttpStatus.FORBIDDEN, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        } catch (Exception e) {
            ErrorRes res = new ErrorRes(HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

//    Edit Data By Id
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<?> getHape(@PathVariable("id") int id,
                                      @ModelAttribute hp req)  {
        hapeService.updateById(id, req);
        return ResponseEntity.ok("Data Berhasil Di Ubah");
    }

//    Get Data By Id
    @GetMapping(path = "/edit/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<hp> editHape(@PathVariable("id") int id){
         var data = hapeService.getById(id);
         return ResponseEntity.ok(data);
    }

//    Tampil Senua data
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<hp>> semuaData(){
        return hapeService.getAllHape();
    }


    //    Delete Data By Id
    @DeleteMapping("hapus/{id}")
    public ResponseEntity<?> hapusData(@PathVariable int id) throws IOException {
            hp hape = hapeService.getById(id);
            String file = path + "uploads/" + hape.getId();
            File fileDel = new File(file);
            FileUtils.deleteDirectory(fileDel);
            if (!fileDel.exists()) {
                this.hapeService.deleteById(id);
            }
            return ResponseEntity.ok("Data Berhasil dihapus");
    }

    //    Get Data Poto By Id
    @GetMapping(path = "/poto/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") int id) {
        try {
            byte[] bytes;
            hp hape = hapeService.getPotobyId(id);
            var img = new ClassPathResource(hape.getImagePath());
            bytes = StreamUtils.copyToByteArray(img.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentType(MediaType.IMAGE_PNG)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);
        } catch (AccessDeniedException e){
            ErrorRes res = new ErrorRes(HttpStatus.FORBIDDEN, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        } catch (Exception e){
            ErrorRes res = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }
}

package com.example.restapi.Controllers;

import com.example.restapi.FileUploadUtil;
import com.example.restapi.Models.Handphone;
import com.example.restapi.Response.ErrorRes;
import com.example.restapi.Service.HapeService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hape")
@CrossOrigin(origins = "*")
public class HapeController {

    @Autowired
    private HapeService hapeService;

//  Tambah Data
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewHape(@ModelAttribute("handphone") Handphone hape,
                             @RequestParam("file") MultipartFile file) throws
            IOException {{
    }
        LocalDate d = LocalDate.now();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        hape.setPublish(Date.valueOf(d));
        hape.setFoto(fileName);
        Handphone saveHape = hapeService.saveHape(hape);
        String uploadDir = "src/main/resources/uploads/" + saveHape.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, file);
        return "Berhasil Di Upload";
    }

//    Edit Data By Id
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Handphone> getHape(@PathVariable("id") int id,
                                             @RequestBody Handphone req)  {
        return hapeService.updateById(id, req);
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
        return hapeService.getAllHape();
    }


//    Delete Data By Id
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("hapus/{id}")
    public ResponseEntity<?> hapusData(@PathVariable int id){
        this.hapeService.deleteById(id);
        return ResponseEntity.ok("Data Berhasil dihapus");
    }

//    Get Data Poto By Id

    @GetMapping(path = "/poto/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") int id) {
        try {
            byte[] bytes;
            Handphone hape = hapeService.getPotobyId(id);
            var img = new ClassPathResource(hape.getImagePath());
            bytes = StreamUtils.copyToByteArray(img.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
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

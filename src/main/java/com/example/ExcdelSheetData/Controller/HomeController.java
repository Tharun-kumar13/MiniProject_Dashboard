package com.example.ExcdelSheetData.Controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExcdelSheetData.Entity.Customer;
import com.example.ExcdelSheetData.Service.CustomerService;
import com.example.ExcdelSheetData.serviceImplementaion.*;

@RestController
public class HomeController {

    @Autowired
    private CustomerServiceImp customerServiceImp;

    @GetMapping("/data")
    public ResponseEntity<List<Customer>> getData() {
        return ResponseEntity.ok().body(customerServiceImp.findAllCust());
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> saveData(@RequestParam("file") MultipartFile file)
            throws EncryptedDocumentException, IOException {

        customerServiceImp.saveFileData(file.getInputStream());

        return ResponseEntity.ok("File Saved");

    }

}

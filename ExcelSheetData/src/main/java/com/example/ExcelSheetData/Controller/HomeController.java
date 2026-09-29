package com.example.ExcelSheetData.Controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExcelSheetData.Entity.Customer;
import com.example.ExcelSheetData.Service.CustomerService;
import com.example.ExcelSheetData.serviceImplementaion.*;

@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // @Autowired
    // private CustomerServiceImp customerServiceImp;

    private final CustomerService customerServiceImp;

    public HomeController(CustomerService customerServiceImp) {
        this.customerServiceImp = customerServiceImp;
    }

    // @GetMapping("/data")
    // public ResponseEntity<List<Customer>> getData() {
    // return ResponseEntity.ok().body(customerServiceImp.findAllCust());
    // }

    @GetMapping("/data")
    public ResponseEntity<?> getData() {
        try {
            List<Customer> customerData = customerServiceImp.findAllCust();
            return ResponseEntity.ok(customerData);
        } catch (Exception exception) {
            logger.error("Unable to fetch customer data", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to fetch customer data. Please try again later.");
        }
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> saveData(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please upload a non-empty Excel file.");
        }

        try (var inputStream = file.getInputStream()) {
            customerServiceImp.saveFileData(inputStream);
            return ResponseEntity.ok("Customer data uploaded successfully.");
        } catch (EncryptedDocumentException | IOException exception) {
            logger.warn("The uploaded Excel file could not be read", exception);
            return ResponseEntity.badRequest()
                    .body("Unable to read the uploaded Excel file. Ensure it is valid and unencrypted.");
        } catch (Exception exception) {
            logger.error("Unable to save customer data from the uploaded file", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to save customer data. Please try again later.");
        }
    }

}

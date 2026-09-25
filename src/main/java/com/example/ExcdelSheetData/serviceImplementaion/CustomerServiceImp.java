package com.example.ExcdelSheetData.serviceImplementaion;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExcdelSheetData.Entity.*;

import com.example.ExcdelSheetData.Repository.*;

@Service
public class CustomerServiceImp {

    @Autowired
    private CustomerRepo customerRepo;

    public void saveFileData(InputStream file) throws EncryptedDocumentException, IOException {
        List<Customer> customerData = new LinkedList<>();

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.forEach(
                row -> {
                    Customer customer = new Customer();

                    if (row.getRowNum() != 0) {
                        customer.setEmployeeCode(row.getCell(0).getNumericCellValue());
                        customer.setEmployeeName(row.getCell(1).getStringCellValue());
                        customer.setProjectCode(row.getCell(2).getStringCellValue());
                        customer.setProjectName(row.getCell(3).getStringCellValue());

                        customerData.add(customer);
                    }
                });

        customerRepo.saveAll(customerData);

    }

    public List<Customer> findAllCust() {
        return customerRepo.findAll();
    }

}

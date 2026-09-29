package com.example.ExcelSheetData.serviceImplementaion;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExcelSheetData.Entity.*;
import com.example.ExcelSheetData.Service.*;

import com.example.ExcelSheetData.Repository.*;

@Service
public class CustomerServiceImp implements CustomerService {

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
                        customer.setAllocation(row.getCell(7).getNumericCellValue());
                        customer.setCustomerCode(row.getCell(11).getNumericCellValue());
                        customer.setCustomerName(row.getCell(12).getStringCellValue());
                        customer.setProjectDUName(row.getCell(20).getStringCellValue());
                        customer.setProjectManagerName(row.getCell(22).getStringCellValue());
                        customer.setProjectCategory(row.getCell(26).getStringCellValue());
                        customer.setProjectCategoryName(row.getCell(27).getStringCellValue());
                        customer.setWbsType(row.getCell(28).getStringCellValue());
                        customer.setBillingStatus(row.getCell(34).getStringCellValue());
                        customer.setEmployeeLobName(row.getCell(38).getStringCellValue());
                        customer.setBand(row.getCell(43).getStringCellValue());
                        customer.setSubBand(row.getCell(44).getStringCellValue());
                        customer.setJoiningDate(row.getCell(49).getStringCellValue());
                        customer.setPsa(row.getCell(50).getStringCellValue());
                        customerData.add(customer);
                    }
                });

        customerRepo.saveAll(customerData);

    }

    public List<Customer> findAllCust() {
        return customerRepo.findAll();
    }

}

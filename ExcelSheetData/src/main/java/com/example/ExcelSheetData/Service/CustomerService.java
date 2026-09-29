package com.example.ExcelSheetData.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.EncryptedDocumentException;

import com.example.ExcelSheetData.Entity.*;

public interface CustomerService {

    void saveFileData(InputStream file) throws EncryptedDocumentException, IOException;

    List<Customer> findAllCust();

}

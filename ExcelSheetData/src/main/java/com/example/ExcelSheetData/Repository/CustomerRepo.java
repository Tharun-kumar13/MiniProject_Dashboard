package com.example.ExcelSheetData.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExcelSheetData.Entity.*;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Double> {

}

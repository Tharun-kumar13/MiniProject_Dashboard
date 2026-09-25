package com.example.ExcdelSheetData.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    private double employeeCode;

    private String employeeName;

    private String projectCode;

    private String projectName;

    public Customer() {
    }

    public Customer(double employeeCode, String employeeName, String projectCode, String projectName) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.projectCode = projectCode;
        this.projectName = projectName;
    }

    public double getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(double employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}

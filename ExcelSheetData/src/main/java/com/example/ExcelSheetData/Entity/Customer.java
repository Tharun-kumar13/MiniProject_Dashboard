package com.example.ExcelSheetData.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    private double employeeCode;

    private String employeeName;

    private String projectCode;

    private String projectName;

    private double allocation;

    private double customerCode;

    private String customerName;

    private String projectDUName;

    private String projectManagerName;

    private String projectCategory;

    private String projectCategoryName;

    private String wbsType;

    private String billingStatus;

    private String employeeLobName;

    private String band;

    private String subBand;

    private String joiningDate;

    private String psa;

    public Customer() {
    }

    public Customer(double employeeCode, String employeeName, String projectCode, String projectName, double allocation,
            double customerCode, String customerName, String projectDUName, String projectManagerName,
            String projectCategory, String projectCategoryName, String wbsType, String billingStatus,
            String employeeLobName, String band, String subBand, String joiningDate, String psa) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.allocation = allocation;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.projectDUName = projectDUName;
        this.projectManagerName = projectManagerName;
        this.projectCategory = projectCategory;
        this.projectCategoryName = projectCategoryName;
        this.wbsType = wbsType;
        this.billingStatus = billingStatus;
        this.employeeLobName = employeeLobName;
        this.band = band;
        this.subBand = subBand;
        this.joiningDate = joiningDate;
        this.psa = psa;
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

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public double getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(double customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectDUName() {
        return projectDUName;
    }

    public void setProjectDUName(String projectDUName) {
        this.projectDUName = projectDUName;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectCategoryName() {
        return projectCategoryName;
    }

    public void setProjectCategoryName(String projectCategoryName) {
        this.projectCategoryName = projectCategoryName;
    }

    public String getWbsType() {
        return wbsType;
    }

    public void setWbsType(String wbsType) {
        this.wbsType = wbsType;
    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getEmployeeLobName() {
        return employeeLobName;
    }

    public void setEmployeeLobName(String employeeLobName) {
        this.employeeLobName = employeeLobName;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getSubBand() {
        return subBand;
    }

    public void setSubBand(String subBand) {
        this.subBand = subBand;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPsa() {
        return psa;
    }

    public void setPsa(String psa) {
        this.psa = psa;
    }

}

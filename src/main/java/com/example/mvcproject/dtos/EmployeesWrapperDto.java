package com.example.mvcproject.dtos;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesWrapperDto {

    @XmlElement(name = "employee")
     private List<EmployeeImportDto> employees;

    public List<EmployeeImportDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeImportDto> employees) {
        this.employees = employees;
    }
}

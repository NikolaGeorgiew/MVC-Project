package com.example.mvcproject.dtos;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyWrapperDto {

    @XmlElement(name = "company")
    private List<CompanyImportDto> companies;

    public List<CompanyImportDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyImportDto> companies) {
        this.companies = companies;
    }
}

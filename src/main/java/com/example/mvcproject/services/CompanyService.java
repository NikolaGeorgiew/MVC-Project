package com.example.mvcproject.services;


import com.example.mvcproject.dtos.CompanyWrapperDto;
import com.example.mvcproject.entities.Company;
import com.example.mvcproject.repositories.CompanyRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }


    public boolean areImported() {

        return this.companyRepository.count() > 0;
    }

    public String getXmlInfo() throws IOException {
       Path path =  Path.of("src/main/resources/files/xmls/companies.xml");

        return String.join("\n", Files.readAllLines(path));
    }

    public void importEntities() throws IOException, JAXBException {
        String xmlContent = this.getXmlInfo().trim();

        ByteArrayInputStream infoStream = new ByteArrayInputStream(xmlContent.getBytes());


        JAXBContext context = JAXBContext.newInstance(CompanyWrapperDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        CompanyWrapperDto companies = (CompanyWrapperDto) unmarshaller.unmarshal(infoStream);

        List<Company> companiesToSave = companies.getCompanies()
                .stream().map(cDto -> this.modelMapper.map(cDto, Company.class))
                .toList();

        this.companyRepository.saveAll(companiesToSave);


    }
}

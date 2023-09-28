package com.example.mvcproject.services;


import com.example.mvcproject.dtos.EmployeeBasicInfo;
import com.example.mvcproject.dtos.EmployeesWrapperDto;
import com.example.mvcproject.entities.Employee;
import com.example.mvcproject.entities.Project;
import com.example.mvcproject.repositories.EmployeeRepository;
import com.example.mvcproject.repositories.ProjectRepository;
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
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    public String getXmlInfo() throws IOException {
        Path path =  Path.of("src/main/resources/files/xmls/employees.xml");

        return String.join("\n", Files.readAllLines(path));
    }

    public void importEntities() throws IOException, JAXBException {
        String xmlContent = this.getXmlInfo().trim();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext jaxbContext =JAXBContext.newInstance(EmployeesWrapperDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        EmployeesWrapperDto employees = (EmployeesWrapperDto) unmarshaller.unmarshal(stream);

        List<Employee> employeesToSave = employees.getEmployees()
                .stream().map(dto -> this.modelMapper.map(dto, Employee.class))
                .map(this::addProject)
                .toList();

        this.employeeRepository.saveAll(employeesToSave);

    }


    private Employee addProject(Employee employee) {
        Project project = this.projectRepository.findFirstByName(employee.getProject().getName()).get();

        employee.setProject(project);
        return employee;
    }

    public String getEmployeesOverAge(int age) {
        return this.employeeRepository.findAllByAgeGreaterThan(age)
                .stream().map(EmployeeBasicInfo::fromEmployee)
                .map(EmployeeBasicInfo::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

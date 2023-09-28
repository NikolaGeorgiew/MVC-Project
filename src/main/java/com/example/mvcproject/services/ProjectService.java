package com.example.mvcproject.services;


import com.example.mvcproject.dtos.ProjectBasicInfo;
import com.example.mvcproject.dtos.ProjectWrapperDto;
import com.example.mvcproject.entities.Project;
import com.example.mvcproject.repositories.CompanyRepository;
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
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    //TODO: move me into service logic
    private final CompanyRepository companyRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }

    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    public String getXmlInfo() throws IOException {
        Path path =  Path.of("src/main/resources/files/xmls/projects.xml");

        return String.join("\n", Files.readAllLines(path));
    }

    public void importEntities() throws IOException, JAXBException {
        String xmlContent = this.getXmlInfo().trim();
        ByteArrayInputStream infoStream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ProjectWrapperDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProjectWrapperDto projects = (ProjectWrapperDto) unmarshaller.unmarshal(infoStream);

        List<Project> projectsToSave = projects.getProjects()
                .stream()
                .map(pDto -> this.modelMapper.map(pDto, Project.class))
                .map(this::addCompany)
                .toList();
        this.projectRepository.saveAll(projectsToSave);
    }

    private Project addCompany(Project project) {
        project.setCompany(this.companyRepository.findFirstByName(project.getCompany().getName())
                .get());
        return project;
    }

    public String getAllFinishedProjects() {
        return this.projectRepository.findAllByIsFinished(true)
                .stream().map(entity -> this.modelMapper.map(entity, ProjectBasicInfo.class))
                .map(ProjectBasicInfo::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
}

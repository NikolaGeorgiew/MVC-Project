package com.example.mvcproject.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectWrapperDto {

    @XmlElement(name = "project")
    private List<ProjectImportDto> projects;

    public List<ProjectImportDto> getProjects() { 
        return projects;
    }

    public void setProjects(List<ProjectImportDto> projects) {
        this.projects = projects;
    }
}

package com.example.mvcproject.controllers;

import com.example.mvcproject.services.EmployeeService;
import com.example.mvcproject.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController {

    private  final ProjectService projectService;
    private final EmployeeService employeeService;

    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;

        this.employeeService = employeeService;
    }

    @GetMapping("export/projects-if-finished")
    public String getAllFinishedProjects(Model model) {
        String allFinishedProjects = this.projectService.getAllFinishedProjects();

        model.addAttribute("projectsIfFinished", allFinishedProjects);
        return "export/export-project-if-finished";
    }

    @GetMapping("export/employees-above-25")
    public String getAllEmployeesAbove25(Model model) {

        model.addAttribute("employeesAbove", this.employeeService.getEmployeesOverAge(25));
        return "export/export-employees-with-age";
    }
}

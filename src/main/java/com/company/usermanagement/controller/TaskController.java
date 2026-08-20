package com.company.usermanagement.controller;

import com.company.usermanagement.constraint.AppConstants;
import com.company.usermanagement.dto.TaskDTO;
import com.company.usermanagement.service.TaskService;
import com.company.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/taskForm")
    public String addTask(Model model) {
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("assignedUsersList", userService.getAllUsers());
        model.addAttribute("fixedOns", AppConstants.getFixedOnList());
        model.addAttribute("prioritiesList", AppConstants.getPriorityList());
        model.addAttribute("issueTypeList", AppConstants.getIssueTypeList());
        model.addAttribute("statusList", AppConstants.getStatusList());
        return "add-task1";
    }

    @PostMapping("/saveTask")
    public String saveTask(TaskDTO taskDTO) {
        System.out.println("TaskDTO: " + taskDTO);
        taskService.saveTask(taskDTO);
        return "redirect:/dashboard";
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        TaskDTO editDTO = taskService.getTaskById(id);
        System.out.println("Edit Task : " + editDTO);

        model.addAttribute("task", editDTO);
        model.addAttribute("assignedUsersList", userService.getAllUsers());
        model.addAttribute("fixedOns", AppConstants.getFixedOnList());
        model.addAttribute("prioritiesList", AppConstants.getPriorityList());
        model.addAttribute("issueTypeList", AppConstants.getIssueTypeList());
        model.addAttribute("statusList", AppConstants.getStatusList());
        return "add-task1";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "redirect:/dashboard";
    }

}

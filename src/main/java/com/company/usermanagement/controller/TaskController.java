package com.company.usermanagement.controller;

import com.company.usermanagement.constraint.AppConstants;
import com.company.usermanagement.dto.TaskDTO;
import com.company.usermanagement.service.TaskService;
import com.company.usermanagement.service.UserService;
import com.company.usermanagement.session.UserLoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserLoginSession userLoginSession;
    @GetMapping
    public String manageTask(Model model){
        boolean allowDelete = false;
        if (!userLoginSession.getRole().name().equalsIgnoreCase("developer")){
            allowDelete=true;
        }
        model.addAttribute("allowDelete",allowDelete);
        model.addAttribute("tasksList",taskService.getAllTasks());
        return "task/task-table";
    }

    @GetMapping("/taskForm")
    public String addTask(Model model) {
        model.addAttribute("UserLoginSession", userLoginSession);
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("assignedUsersList", userService.getAllUsers());
        model.addAttribute("fixedOns", AppConstants.getFixedOnList());
        model.addAttribute("prioritiesList", AppConstants.getPriorityList());
        model.addAttribute("issueTypeList", AppConstants.getIssueTypeList());
        model.addAttribute("statusList", AppConstants.getStatusList());
        return "task/add-task";
    }

    @PostMapping("/saveTask")
    public String saveTask(TaskDTO taskDTO) {
        System.out.println("TaskDTO: " + taskDTO);
        taskService.saveTask(taskDTO);
        return "redirect:/tasks";
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
        return "task/add-task";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/myTask")
    public String myTask(Model model){
        boolean allowDelete = false;
        if (!userLoginSession.getRole().name().equalsIgnoreCase("developer")){
            allowDelete=true;
        }
        model.addAttribute("allowDelete",allowDelete);
        List<Long> userIds = new ArrayList<>();
        userIds.add(userLoginSession.getUserId());
        userIds.add(1L); // temp users
        model.addAttribute("tasksList",taskService.getMyAllTasks(userIds));
        return "task/task-table";
    }
}

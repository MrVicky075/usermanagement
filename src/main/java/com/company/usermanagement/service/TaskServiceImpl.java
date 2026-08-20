package com.company.usermanagement.service;


import com.company.usermanagement.dto.TaskDTO;
import com.company.usermanagement.entity.TaskEntity;
import com.company.usermanagement.exception.ResourceNotFoundException;
import com.company.usermanagement.mapper.TaskMapper;
import com.company.usermanagement.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Override
    @Transactional
    public TaskDTO saveTask(TaskDTO taskDTO) {
        return mapper.toDTO(taskRepository.save(mapper.toEntity(taskDTO)));
    }

    @Transactional
    public List<TaskEntity> saveAll(List<TaskEntity> tasks) {
        return taskRepository.saveAll(tasks);
    }
    @Override
    public TaskDTO getTaskById(Long taskId) {
        return mapper.toDTO(taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId)));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return mapper.toDTOList(taskRepository.findAll());
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if(existingTask == null){
            new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        mapper.updateTaskEntity(taskDTO, existingTask);
        return mapper.toDTO(taskRepository.save(existingTask));
    }

    @Override
    public void deleteTask(Long taskId) {
        TaskEntity existingTask = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if(existingTask == null){
            new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.delete(existingTask);
    }

    @Override
    public List<TaskDTO> getFilteredTasks(String assignedTo, String issueType, String priority, String status, String fixedOn) {
        List<TaskEntity> tasks = taskRepository.findAll();

        // Apply filters
        List<TaskEntity> filteredTasks = tasks.stream()
                .filter(task -> {
                    // Filter by assigned user
                    if (!"all".equals(assignedTo) && assignedTo != null) {
                        Long assignUserId = Long.parseLong(assignedTo);
                        if (task.getAssignedUser() == null ||
                                !task.getAssignedUser().getUserId().equals(assignUserId)) {
                            return false;
                        }
                    }

                    // Filter by issue type
                    if (!"all".equals(issueType) && issueType != null) {
                        if (task.getIssue() == null || !task.getIssue().equals(issueType)) {
                            return false;
                        }
                    }

                    // Filter by priority
                    if (!"all".equals(priority) && priority != null) {
                        if (task.getPriority() == null || !task.getPriority().equals(priority)) {
                            return false;
                        }
                    }

                    // Filter by status
                    if (!"all".equals(status) && status != null){
                        if (task.getStatus() == null || !task.getStatus().equals(status)){
                            return false;
                        }
                    }

                    // Filter by fixed On
                    if (!"all".equals(fixedOn) && fixedOn != null){
                        if (task.getFixedOn() == null || !task.getFixedOn().equals(fixedOn)){
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());

        // Convert to DTOs
        return mapper.toDTOList(filteredTasks);
    }
}

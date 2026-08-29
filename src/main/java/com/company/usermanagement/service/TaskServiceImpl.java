package com.company.usermanagement.service;


import com.company.usermanagement.dto.TaskDTO;
import com.company.usermanagement.entity.TaskEntity;
import com.company.usermanagement.exception.ResourceNotFoundException;
import com.company.usermanagement.mapper.TaskMapper;
import com.company.usermanagement.repository.TaskRepository;
import com.company.usermanagement.session.UserLoginSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final UserLoginSession userLoginSession;

    @Override
    @Transactional
    public TaskDTO saveTask(TaskDTO taskDTO) {
        Long currentUser= userLoginSession.getUserId();
        taskDTO.setCreateBy(currentUser);
        taskDTO.setUpdatedBy(currentUser);
        taskDTO.setCreateAt(LocalDateTime.now());
        taskDTO.setUpdateAt(LocalDateTime.now());
        taskDTO.setIsActive(true);
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
        return mapper.toDTOList(taskRepository.getAllTask());
    }

    @Override
    public List<TaskDTO> getMyAllTasks(List<Long> userIds) {
        return mapper.toDTOList(taskRepository.getMyAllTask(userIds));
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if(existingTask == null){
            new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        taskDTO.setCreateBy(existingTask.getCreatedBy());
        taskDTO.setUpdatedBy(userLoginSession.getUserId());
        taskDTO.setCreateAt(existingTask.getCreateAt());
        taskDTO.setUpdateAt(LocalDateTime.now());
        mapper.updateTaskEntity(taskDTO, existingTask);
        return mapper.toDTO(taskRepository.save(existingTask));
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity existingTask = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if(existingTask == null){
            new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.deleteTaskById(taskId);
    }

    @Override
    public List<TaskDTO> getFilteredTasks(String client, String assignedTo, String issueType, String priority, String status, String fixedOn, String dateFrom, String dateTo) {
        Long assignedUserId = null;
        if (!"all".equals(assignedTo) && assignedTo != null && !assignedTo.isEmpty()) {
            try {
                assignedUserId = Long.parseLong(assignedTo);
            } catch (NumberFormatException e) {
                // ignore - keep as null
            }
        }

        // Parse date strings to LocalDateTime
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;

        if (dateFrom != null && !dateFrom.isEmpty()) {
            try {
                fromDate = LocalDateTime.parse(dateFrom + "T00:00:00");
            } catch (Exception e) {
                // ignore
            }
        }

        if (dateTo != null && !dateTo.isEmpty()) {
            try {
                toDate = LocalDateTime.parse(dateTo + "T23:59:59");
            } catch (Exception e) {
                // ignore
            }
        }

        // Use repository query
        List<TaskEntity> tasks = taskRepository.findTasksWithFilters(
                assignedUserId, issueType, priority, status, fixedOn, client, fromDate, toDate
        );

        return mapper.toDTOList(tasks);
    }

    @Override
    public List<String> getDistinctClientNames() {
        return taskRepository.findDistinctClientNames();
    }
}

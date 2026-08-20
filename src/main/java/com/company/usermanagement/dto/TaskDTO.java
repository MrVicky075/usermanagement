package com.company.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long taskId;

    @NotNull(message = "Assign ID is required")
    private Long assignUserId;
    private String assignUserName;

    private String priority = "-";

    @NotBlank(message = "Client name is required")
    @Size(max = 255, message = "Client name must be less than 255 characters")
    private String clientName;

    private String issue;

    private Long redmineId=0L;

    private Integer days;

    @Size(max = 5000, message = "Requirement must be less than 5000 characters")
    private String requirement;

    @Size(max = 5000, message = "Task details must be less than 5000 characters")
    private String taskDetails;

    private String status = "-";

    private String fixedOn="-";

    @Size(max = 2000, message = "Remarks must be less than 2000 characters")
    private String remarks;

    private String isActive;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}

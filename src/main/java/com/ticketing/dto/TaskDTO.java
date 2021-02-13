package com.ticketing.dto;

import com.ticketing.enums.Status;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status taskStatus;
    private LocalDate assignedDate;
}

package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTOOut {
    private Integer id;
    private Integer userId;
    private String message;
    private String status;
    private String type;
    private Integer referenceId;
    private String referenceType;
    private LocalDateTime createdAt;
    private Boolean sentByEmail;
    private Boolean sentByWhatsApp;
}

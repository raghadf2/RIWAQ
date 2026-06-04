package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.NotificationDTOIn;
import com.example.riwaq.Service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getNotificationById(@PathVariable Integer id) {
//        return ResponseEntity.ok(notificationService.getNotificationById(id));
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addNotification(@RequestBody @Valid NotificationDTOIn dto) {
//        notificationService.addNotification(dto);
//        return ResponseEntity.ok(new ApiResponse("Notification created successfully"));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteNotification(@PathVariable Integer id) {
//        notificationService.deleteNotification(id);
//        return ResponseEntity.ok(new ApiResponse("Notification deleted successfully"));
//    }
}

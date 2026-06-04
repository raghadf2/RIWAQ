package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.SpaceDTOIn;
import com.example.riwaq.Service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping("/add")
    public ResponseEntity<?> addSpace(@RequestBody @Valid SpaceDTOIn dto){
        spaceService.addSpace(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Space added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllSpaces(){
        return ResponseEntity.status(200).body(spaceService.getAllSpaces());
    }

    @PutMapping("/update/{spaceId}")
    public ResponseEntity<?> updateSpace(@PathVariable Integer spaceId, @RequestBody @Valid SpaceDTOIn dto){
        spaceService.updateSpace(spaceId,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Space updated successfully"));
    }

    @DeleteMapping("/delete/{spaceId}")
    public ResponseEntity<?> deleteSpace(@PathVariable Integer spaceId){
        spaceService.deleteSpace(spaceId);
        return ResponseEntity.status(200).body(new ApiResponse("Space deleted successfully"));
    }

}

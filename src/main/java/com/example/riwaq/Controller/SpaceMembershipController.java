package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.SpaceMembershipDTOIn;
import com.example.riwaq.Service.SpaceMembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/space-membership")
@RequiredArgsConstructor
public class SpaceMembershipController {

    private final SpaceMembershipService spaceMembershipService;

    @PostMapping("/add")
    public ResponseEntity<?> addMembership(@RequestBody @Valid com.example.riwaq.DTO.IN.PostDTOIn.SpaceMembershipDTOIn dto){
        spaceMembershipService.addMembership(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Membership added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllMemberships(){
        return ResponseEntity.status(200).body(spaceMembershipService.getAllMemberships());
    }

//    @PutMapping("/update/{membershipId}")
//    public ResponseEntity<?> updateMembership(@PathVariable Integer membershipId, @RequestBody @Valid com.example.riwaq.DTO.IN.PostDTOIn.SpaceMembershipDTOIn dto){
//        spaceMembershipService.updateMembership(membershipId,dto);
//        return ResponseEntity.status(200).body(new ApiResponse("Membership updated successfully"));
//    }
//
//    @DeleteMapping("/delete/{membershipId}")
//    public ResponseEntity<?> deleteMembership(@PathVariable Integer membershipId){
//        spaceMembershipService.deleteMembership(membershipId);
//        return ResponseEntity.status(200).body(new ApiResponse("Membership deleted successfully"));
//    }
}

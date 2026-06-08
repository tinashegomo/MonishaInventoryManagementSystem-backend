package com.tinasheGomo.MonishaInventoryManagementSystem.controller.user;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.UserRole;
import com.tinasheGomo.MonishaInventoryManagementSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monishaInventory/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/monishaInventory/user/get-current-user
    @GetMapping("/get-current-user")
    public ResponseEntity<UserResponseDTO> getCurrentLoggedInUser() {
        UserResponseDTO response = userService.getCurrentLoggedInUser();
        return ResponseEntity.ok(response);
    }

    // GET /api/monishaInventory/user/get-current-user-role
    @GetMapping("/get-current-user-role")
    public ResponseEntity<UserRole> getCurrentLoggedInUserRole() {
        UserRole response = userService.getCurrentLoggedInUserRole();
        return ResponseEntity.ok(response);
    }

    // GET /api/monishaInventory/user/get-all-users
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    // GET /api/monishaInventory/user/get-user-byId/{id}
    @GetMapping("/get-user-byId/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable UUID id) {
        UserResponseDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/monishaInventory/user/delete-user/{id}
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/monishaInventory/user/update-user-role/{id}
    @PutMapping("/update-user-role/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @PathVariable UUID userId,
            @RequestParam UserRole userRole) {
        UserResponseDTO response = userService.updateUserRole(userId, userRole);
        return ResponseEntity.ok(response);
    }
}

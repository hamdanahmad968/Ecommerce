package com.ecom.app.controller;

import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.User;
import com.ecom.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());

    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
//        User user = userService.fetchUser(id);
//        if (user == null) return ResponseEntity.notFound().build();
//       return ResponseEntity.ok(user);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("user Added Successfully");
    }
    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser( @PathVariable Long id ,@RequestBody UserRequest updateUserRequest ){
       boolean updated = userService.updateUser(id, updateUserRequest);
       if(updated){
          return ResponseEntity.ok("User Updated Successfully");
       }
           return ResponseEntity.notFound().build();
    }
}

package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.Course;
import com.eduhub.eduhub_backend.component.User;
import com.eduhub.eduhub_backend.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    static List<User> userList=new ArrayList<>();
    static {
        userList.add(new User(101, "Karthi", "1234"));
        userList.add(new User(102, "Jeni", "1234"));
        userList.add(new User(103, "Suriya", "1234"));
        userList.add(new User(104, "Lavi", "1234"));
        userList.add(new User(105, "Harini", "1234"));
    }
    @GetMapping("/gu")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/gu/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        if(id.matches(".*[^a-zA-Z0-9].*")){
            throw new IllegalArgumentException("It is having a special character");
        }
        int userId = Integer.parseInt(id);
        return userList.stream().filter(u -> u.getUserId() == userId)
                .findFirst().map(ResponseEntity::ok).orElseThrow(()->new ResourceNotFoundException("userId","userName",String.valueOf(userId)));
    }
    @GetMapping("uquery")
    public ResponseEntity<User> getuser(@RequestParam String id){
        if(id.matches(".*[^a-zA-Z0-9].*")){
            throw new IllegalArgumentException("It is having a special character");
        }
        int userId = Integer.parseInt(id);
        return userList.stream().filter(u -> u.getUserId() == userId)
                .findFirst().map(ResponseEntity::ok).orElseThrow(()->new ResourceNotFoundException("userId","userName",String.valueOf(userId)));
    }
    @PostMapping("cu")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userList.add(user);
        return ResponseEntity.ok(user);
    }
    @PutMapping("{userId}/upd")
    public ResponseEntity updatePassword(@PathVariable("userId") int userId,
                                       @RequestBody User updateUser){

        User user=userList.stream().filter(u -> u.getUserId() == userId)
                .findFirst().orElseThrow(()->new ResourceNotFoundException("userId","userName",String.valueOf(userId)));
        user.setPassword(updateUser.getPassword());
        return ResponseEntity.accepted().body(user);
    }
    @DeleteMapping("/{userid}/del")
    public ResponseEntity deleteUser(
            @PathVariable("userid") int userId) {
        User user=userList.stream().filter(u -> u.getUserId() == userId)
                .findFirst().orElseThrow(()->new ResourceNotFoundException("userId","userName",String.valueOf(userId)));
        userList.remove(user);
        return ResponseEntity.accepted().body("deleted successfully");
    }
}

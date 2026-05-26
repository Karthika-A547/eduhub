package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    //http://localhost:8080/student
    @GetMapping("/student")
    public ResponseEntity<Student>getStudent(){
        Student student=new Student(1,"Ram","Kumar");
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    //http://localhost:8080/students
    @GetMapping("/students")
    public ResponseEntity<List<Student>>getStudents(){
        List<Student>studentList=new ArrayList<>();
        studentList.add(new Student(1,"Ram","Pr"));
        studentList.add(new Student(2,"Ravi","Mohan"));
        studentList.add(new Student(3,"Raj","Kumar"));
        studentList.add(new Student(4,"Raja","Prakash"));
        return new ResponseEntity<>(studentList,HttpStatus.OK);
    }

    //http://localhost:8080/1/karthi/suriya
    @GetMapping("{id}/{first-name}/{last-name}")
    public ResponseEntity<Student>studentPathVariable(@PathVariable("id") int studentId,
                                      @PathVariable("first-name")String firstName,
                                      @PathVariable("last-name")String lastName
                                     )
    {
        Student student=new Student(studentId,firstName,lastName);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    //http://localhost:8080/query?studentId=1&firstName=Ram&lastName=Kumar
    @GetMapping("query")
    public ResponseEntity<Student>studentRequestVaraible(@RequestParam int studentId,
                                                         @RequestParam String firstName,
                                                         @RequestParam String lastName)
    {
         Student student=new Student(studentId,firstName,lastName);
         return ResponseEntity.ok(student);
    }

    //http://localhost:8080/create
    @PostMapping("create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        System.out.println(student.getId());
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return ResponseEntity.ok(student);
    }

    //http://localhost:8080/update
    @PutMapping("update")
    public ResponseEntity updateStudent(){
        return ResponseEntity.accepted().body("It does not have business logic");
    }

    //http://localhost:8080/1/update
    @PutMapping("{id}/update")
    public ResponseEntity updateStudent(@PathVariable("id") int studuentId,
                                         @RequestBody Student student){
        return ResponseEntity.accepted().body(student);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteStudent(
            @PathVariable("id") int studentId) {

        return ResponseEntity.accepted().body("deleted successfully");
    }

}

package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.Course;
import com.eduhub.eduhub_backend.component.CourseService;
import com.eduhub.eduhub_backend.component.DeptService;
import com.eduhub.eduhub_backend.component.Student;
import com.eduhub.eduhub_backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/course")
@RestController
public class CourseController {
    //    @Autowired
    CourseService courseService;
    //@Autowired
    DeptService deptService;
    public CourseController(CourseService courseService, DeptService deptService){
        this.courseService =courseService;
        this.deptService=deptService;
    }


    @GetMapping("get-course")

    public String getCourse(){
        return courseService.getCourse();
    }
    @GetMapping("get-dept")
    public String getDept(){
        return deptService.getDept();
    }

    static List<Course>courseList=new ArrayList<>();
    static {
        courseList.add(new Course("CS101", "Python", 3));
        courseList.add(new Course("CS102", "Java", 4));
        courseList.add(new Course("CS103", "Sql", 4));
        courseList.add(new Course("CS104", "C++", 3));
        courseList.add(new Course("CS105", "DBMS", 3));
    }
    @GetMapping("/gc")
    public ResponseEntity<List<Course>> getAllCourse(){
        return ResponseEntity.ok(courseList);
    }
    //http://localhost:8080/course/gc/CS105
    @GetMapping("/gc/{courseCode}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseCode){
        return courseList.stream().filter(c->c.getCourseCode().equalsIgnoreCase(courseCode))
            .findFirst().map(ResponseEntity::ok).orElseThrow(()->new ResourceNotFoundException("Course","CourseCode",courseCode));
    }
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses(){
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    //http://localhost:8080/course/CS106/Js/3
    @GetMapping("/course/{course-code}/{subject-name}/{credits}")
    public ResponseEntity<Course>coursePathVariable(@PathVariable("course-code") String courseCode,
                                                      @PathVariable("subject-name")String subjectName,
                                                      @PathVariable("credits")int credits)
    {
        Course course =new Course(courseCode,subjectName,credits);
        return new ResponseEntity<>(course,HttpStatus.OK);
    }

    //http://localhost:8080/cquery?courseCode=103&subjectName=Java&credits=4
    @GetMapping("cquery")
    public ResponseEntity<Course>courseRequestVariable(@RequestParam String courseCode,
                                                       @RequestParam String subjectName,
                                                       @RequestParam int credits)
    {
        Course course=new Course(courseCode,subjectName,credits);
        return ResponseEntity.ok(course);
    }
    @PutMapping("/query/{code}")
    public String queryCourse(@PathVariable String code){
        if(code.startsWith("*")){
            throw new IllegalArgumentException(("It is having a special character"));
        } else if (code.startsWith("6")) {
            throw new RuntimeException();

        }
        return code;
    }

    //http://localhost:8080/cc
    @PostMapping("cc")
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        courseList.add(course);
        return ResponseEntity.ok(course);
    }

    @PutMapping("{courseCode}/upd")
    public ResponseEntity updateCourse(@PathVariable("courseCode") String courseCode,
                                        @RequestBody Course updateCourse){

        Course course=courseList.stream().filter(c->c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Course","CourseCode",courseCode));
        course.setCourseCode(updateCourse.getCourseCode());
        course.setCredits(updateCourse.getCredits());
        return ResponseEntity.accepted().body(course);
    }

    @DeleteMapping("/{courseCode}/del")
    public ResponseEntity deleteCourse(
            @PathVariable("courseCode") String courseCode) {
        Course course=courseList.stream().filter(c->c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Course","CourseCode",courseCode));
        courseList.remove(course);
        return ResponseEntity.accepted().body("deleted successfully");
    }


}
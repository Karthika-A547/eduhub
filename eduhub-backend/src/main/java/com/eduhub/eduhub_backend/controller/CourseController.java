package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.CourseService;
import com.eduhub.eduhub_backend.component.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
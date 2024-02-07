package com.teamsparta.courseregistration.domain.course.controller

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/courses")
@RestController
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping()
    fun getCourseList():ResponseEntity<List<CourseResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body((courseService.getAllCourseList()))
    }

    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable courseId : Long
    ) : ResponseEntity<CourseResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourseById(courseId))
    }

    @PostMapping()
    fun createCourse(@RequestBody createCourseRequest: CreateCourseRequest
    ) : ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.createCourse(createCourseRequest))
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Long,
                     @RequestBody updateCourseRequest: UpdateCourseRequest
    ) : ResponseEntity<CourseResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateCourse(courseId, updateCourseRequest))
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(@PathVariable courseId: Long) : ResponseEntity<Unit> {
        courseService.deleteCourse(courseId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
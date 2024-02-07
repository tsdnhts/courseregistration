package com.teamsparta.courseregistration.domain.user.controller

import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val courseService: CourseService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequest : SignUpRequest) : ResponseEntity<UserResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.signup(signUpRequest))
    }

    @PutMapping("/users/{userid}/profile")
    fun updateUserProfile(@PathVariable userid : Long,
                          @RequestBody updateUserProfileRequest : UpdateUserProfileRequest
    ) : ResponseEntity<UserResponse>{
        // ProfileResponse를 줘도 된다
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateUserProfile(userid, updateUserProfileRequest))
    }
}
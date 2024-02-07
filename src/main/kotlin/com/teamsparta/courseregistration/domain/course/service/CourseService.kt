package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest
import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import org.springframework.http.ResponseEntity


interface CourseService {
    fun getAllCourseList(): List<CourseResponse>

    fun getCourseById(courseId: Long): CourseResponse

    fun createCourse(request: CreateCourseRequest): CourseResponse

    fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse

    fun deleteCourse(courseId: Long)

    fun getLectureList(courseId: Long) : List<LectureResponse>

    fun getLectureById(courseId: Long, lectureid : Long) : LectureResponse

    fun addLecture(courseId: Long, request: AddLectureRequest) : LectureResponse

    fun updateLecture(courseId: Long,
                      lectureid: Long,
                      request: UpdateLectureRequest) : LectureResponse

    fun removeLecture(courseId: Long,lectureid: Long)

    fun getCourseApplicationList(courseId: Long) : List<CourseApplicationResponse>

    fun getApplication(courseId: Long,
                       applicationId : Long
    ) : CourseApplicationResponse

    fun applyCourse(courseId: Long, request: ApplyCourseRequest) : CourseApplicationResponse

    fun updateCourseApplicationStatus(courseId: Long,
                                      applicationId: Long,
                                      request : UpdateApplicationStatusRequest
    ) : CourseApplicationResponse

    fun signup(request : SignUpRequest) : UserResponse

    fun updateUserProfile(userid : Long, request: UpdateUserProfileRequest) : UserResponse


}
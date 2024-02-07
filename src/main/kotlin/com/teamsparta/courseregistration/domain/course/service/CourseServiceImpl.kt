package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.CourseStatus
import com.teamsparta.courseregistration.domain.course.model.toResponse
import com.teamsparta.courseregistration.domain.course.repository.CourseRepository
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import com.teamsparta.courseregistration.domain.courseapplication.repository.CourseApplicationRepository
import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import com.teamsparta.courseregistration.domain.lecture.model.toResponse
import com.teamsparta.courseregistration.domain.lecture.repository.LectureRepository
import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val lectureRepository: LectureRepository,
    private val courseApplicationRepository: CourseApplicationRepository,
    private val userRepository: UserRepository,
) : CourseService {
    override fun getAllCourseList(): List<CourseResponse> {
        return courseRepository.findAll().map { it.toResponse() }
    }

    override fun getCourseById(courseId: Long): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        return course.toResponse()
    }

    @Transactional
    override fun createCourse(request: CreateCourseRequest): CourseResponse {
        return courseRepository.save(
            Course(
                title = request.title,
                description = request.description,
                status = CourseStatus.CLOSED
            )
        ).toResponse()
    }

    @Transactional
    override fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse {
       val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val(title, description) = request

        course.title = title
        course.description = description

        return courseRepository.save(course).toResponse()
    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId)?:throw ModelNotFoundException("Course", courseId)
        courseRepository.delete(course)
    }

    override fun getLectureList(courseId: Long): List<LectureResponse> {
        val lecture = lectureRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        return lectureRepository.findAll().map { it.toResponse() }
    }

    override fun getLectureById(courseId: Long, lectureid: Long): LectureResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun addLecture(courseId: Long, request: AddLectureRequest): LectureResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateLecture(courseId: Long, lectureid: Long, request: UpdateLectureRequest): LectureResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun removeLecture(courseId: Long, lectureid: Long) {
        TODO("Not yet implemented")
    }

    override fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse> {
        TODO("Not yet implemented")
    }

    override fun getApplication(courseId: Long, applicationId: Long): CourseApplicationResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun applyCourse(courseId: Long, request: ApplyCourseRequest): CourseApplicationResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun signup(request: SignUpRequest): UserResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateUserProfile(userid: Long, request: UpdateUserProfileRequest): UserResponse {
        TODO("Not yet implemented")
    }
}
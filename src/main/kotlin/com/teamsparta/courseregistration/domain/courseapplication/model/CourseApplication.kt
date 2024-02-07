package com.teamsparta.courseregistration.domain.courseapplication.model

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.toResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.user.model.User
import com.teamsparta.courseregistration.domain.user.model.toResponse
import jakarta.persistence.*

@Entity
@Table(name = "course_application")
class CourseApplication(

    @Enumerated
    @Column(name = "status")
    var status: CourseApplicationStatus,

    @ManyToOne()
    @JoinColumn(name = "course_id", nullable = false)
    val course: Course,

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    val user: User


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
fun CourseApplication.toResponse(): CourseApplicationResponse {
    return CourseApplicationResponse(
        id = id!!,
        status = status.name,
        course = course.toResponse(),
        user = user.toResponse()
    )
}
package com.teamsparta.courseregistration.domain.course.model

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplication
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import jakarta.persistence.*

@Entity
@Table(name = "course")
class Course (
    @Column(name = "title")
    var title : String,

    @Column(name = "description")
    var description : String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status : CourseStatus,

    @Column(name = "max_applicants")
    val maxApplicants : Int = 30,

    @Column(name = "num_applicants")
    val numApplicants : Int = 0,

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var lectures: MutableList<Lecture> = mutableListOf(),

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val courseApplications : MutableList<CourseApplication> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

//fun close(){
//    status = CourseStatus.CLOSED
//}
//fun addLecture(lecture: Lecture)
//{
//    lectures.add(lecture)
//}
    fun Course.toResponse(): CourseResponse{
        return CourseResponse(
            id = id!!,
            title = title,
            description = description,
            status = status.name,
            maxApplicants = maxApplicants,
            numApplicants = numApplicants
        )
    }
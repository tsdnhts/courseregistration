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
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplication
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicationStatus
import com.teamsparta.courseregistration.domain.courseapplication.model.toResponse
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
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
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
    override fun updateCourse(
        courseId: Long,
        request: UpdateCourseRequest
    ): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        val (title, description) = request

        course.title = title
        course.description = description

        return course.toResponse()
//        return courseRepository.save(course).toResponse()
//        update는 dirty checking으로 저장이 되어 따로 courseRepository.save(course) 이런식으로 기입하지 않아도 된다.
//        추후 테스트 할때 두가지를 바꿔 가면서 테스트해 보자
    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        courseRepository.delete(course)
    }

    override fun getLecture(
        courseId: Long,
        lectureid: Long
    ): LectureResponse {
        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureid)
            ?: throw ModelNotFoundException("Lecture", lectureid)

        return lecture.toResponse()
    }

    override fun getLectureList(courseId: Long): List<LectureResponse> {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        return course.lectures.map { it.toResponse() }
    }

    @Transactional
    override fun addLecture(
        courseId: Long,
        request: AddLectureRequest
    ): LectureResponse {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)

        val lecture = Lecture(
            title = request.title,
            videoUrl = request.videoUrl,
            course = course,
        )
        return lecture.toResponse()
    }

    @Transactional
    override fun updateLecture(
        courseId: Long,
        lectureid: Long,
        request: UpdateLectureRequest
    ): LectureResponse {
        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureid)
            ?: throw ModelNotFoundException("Lecture", lectureid)
        val (title, videoUrl) = request
        lecture.title = title
        lecture.videoUrl = videoUrl
// 해체 선언시 각각의 component에 맞는 함수를 제공해야 한다
// 따라서 title, videoUrl, course를 인자로 넣으면 course와 관련된 component 함수를 따로 정의하지 않는다면 오류가 발생
// course를 제외하고 나머지 2개만 선언
        return lectureRepository.save(lecture).toResponse()
    }

////        return courseRepository.save(course).toResponse()
////        update는 dirty checking으로 저장이 되어 따로 courseRepository.save(course) 이런식으로 기입하지 않아도 된다.
////       추후 테스트 할때 두가지를 바꿔 가면서 테스트해 보자
// 테스트를 위해 updatelecture에는 아래 방법으로 작성함
//    }

    @Transactional
    override fun removeLecture(
        courseId: Long, lectureid: Long
    ) {
        val lecture = lectureRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Lecture", lectureid)
        lectureRepository.delete(lecture)
    }
    // 해답 코드와 다르게 작성한 코드로 추후 실행시 제대로 작동하나 확인이 필요함

    override fun getCourseApplication(
        courseId: Long,
        applicationId: Long
    ): CourseApplicationResponse {
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException(
                "courseApplication",
                applicationId
            )
        return application.toResponse()
    }

    override fun getCourseApplicationList(
        courseId: Long
    ): List<CourseApplicationResponse> {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        return course.courseApplications.map { it.toResponse() }
//        return courseApplicationRepository.findAll().map { it.toResponse() }
    }

    @Transactional
    override fun applyCourse(
        courseId: Long,
        request: ApplyCourseRequest
    ): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw ModelNotFoundException("User", request.userId)
// course 마감여부 체크
        if (course.isClosed()) {
            throw IllegalStateException(" 코스가 이미 닫혔습니다. courseId : $courseId")
        }
// courseApplication 중복 체크
        if (courseApplicationRepository.existsByCourseIdAndUserId(courseId, request.userId)) {
            throw IllegalStateException("이미 지원하셨습니다. courseId : $courseId, userId : ${request.userId}")
        }

        val courseApplication = CourseApplication(
            course = course,
            user = user
            // 엔티티에 pending으로 기본값을 정의했기 때문에 별도 표기할 필요 x
            // 필요시
            // status = CourseApplicationStatus.PENDING을 추가해 주면 된다.
        )
        course.addCourseApplication(courseApplication)
        // CourseApplication에 명속성을 전파
        courseRepository.save(course)

        return courseApplication.toResponse()
    }


    @Transactional
    override fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw ModelNotFoundException("Course", courseId)
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("courseApplication", applicationId)
// 이미 승인 혹은 거절된 신청건인지 확인
        if (application.isProceeded()) {
            throw IllegalStateException("지원이 이미 승인되었습니다. 지원번호 : $applicationId")
        }
// 여기서 사용되는 isProceeded, isClosed 등은 courseApplication 엔티티 내부에 로직을 작성했다.
        // 로직을 작성하지 않고 course.status 등으로 작성해도 되지만, 코드의 가독성을 높이기 위해서 작성했다고 한다.
        // 추후 내가 이 기능을 잘 활용할 수 있도록 더 알아둘 필요가 있다.

        // 코스 마감여부 확인
        if (course.isClosed()) {
            throw IllegalStateException("코스는 이미 마감되었습니다. courseId : $courseId")
        }
// 승인 / 거절에 따른 처리

        when (request.status) {
            //승인인 경우
            CourseApplicationStatus.ACCEPTED.name -> {
                // 승인 처리
                application.accept()
                // Course의 신청 인원 증가
                course.addApplicant()
                // 신청 인원이 꽉 찼다면 마감 처리
                if (course.isFull()) {
                    course.close()
                }
                courseRepository.save(course)
            }

            //거절일 때
            CourseApplicationStatus.REJECTED.name ->{
                //거절 처리
                application.reject()

            }
            // 승인 거절이 아닌 다른 인자가 들어올 경우 에러 처리
            else -> {
                throw  IllegalArgumentException("Invalid status : ${request.status}")
            }
        }
        return  courseApplicationRepository.save(application).toResponse()
    }
    }

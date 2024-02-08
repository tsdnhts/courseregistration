package com.teamsparta.courseregistration.domain.user.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import com.teamsparta.courseregistration.domain.user.model.Profile
import com.teamsparta.courseregistration.domain.user.model.User
import com.teamsparta.courseregistration.domain.user.model.UserRole
import com.teamsparta.courseregistration.domain.user.model.toResponse
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    @Transactional
    override fun signup(request: SignUpRequest): UserResponse {
        if(userRepository.existsByEmail(request.email)){
            throw IllegalStateException("이미 사용중인 이메일입니다")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = request.password,
                profile = Profile(
                    nickname = request.nickname
                ),
                role = when(request.role){
                    "STUDENT" -> UserRole.STUDENT
                    // 위 내용은 다르게도 표기가 가능하다
                    //UserRole.STUDENT.name -> UserRole.STUDENT
                    "TUTOR" -> UserRole.TUTOR
                    else -> throw IllegalArgumentException("존재하지 않는 역할입니다")
                }


            )
        ).toResponse()
    }

    @Transactional
    override fun updateUserProfile(
        userid: Long,
        request: UpdateUserProfileRequest
    ): UserResponse {
        val user = userRepository.findByIdOrNull(userid) ?: throw ModelNotFoundException("User", userid)
        user.profile = Profile(
            nickname = request.nickname
        )
        return userRepository.save(user).toResponse()
    }
}
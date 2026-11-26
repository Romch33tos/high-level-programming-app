package com.romch33tos.accesscontrol.application.auth

import com.romch33tos.accesscontrol.application.dto.AccessRequest
import com.romch33tos.accesscontrol.domain.repository.UserRepository
import org.springframework.stereotype.Service

interface Authenticator {
  fun authenticate(request: AccessRequest): AuthResult
}

@Service
class DefaultAuthenticator(
  private val users: UserRepository
) : Authenticator {
  override fun authenticate(request: AccessRequest): AuthResult {
    val user = users.findUser(request.login) ?: return AuthResult.BadLogin
    return if (users.verifyPassword(user, request.password)) {
      AuthResult.Success(user)
    } else {
      AuthResult.BadPassword
    }
  }
}
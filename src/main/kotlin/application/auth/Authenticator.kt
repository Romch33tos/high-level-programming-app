package application.auth

import application.dto.AccessRequest
import domain.repository.UserRepository
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

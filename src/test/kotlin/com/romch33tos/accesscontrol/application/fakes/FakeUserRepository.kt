package com.romch33tos.accesscontrol.application.fakes

import com.romch33tos.accesscontrol.domain.model.User
import com.romch33tos.accesscontrol.domain.repository.UserRepository

class FakeUserRepository(
  private val users: Map<String, User> = emptyMap(),
  private val passwords: Map<String, String> = emptyMap()
) : UserRepository {
  override fun findUser(login: String): User? = users[login]

  override fun verifyPassword(user: User, password: String): Boolean =
    passwords[user.login] == password
}

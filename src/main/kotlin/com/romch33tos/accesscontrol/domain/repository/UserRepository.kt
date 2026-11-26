package com.romch33tos.accesscontrol.domain.repository

import com.romch33tos.accesscontrol.domain.model.User

interface UserRepository {
  fun findUser(login: String): User?
  fun verifyPassword(user: User, password: String): Boolean
}
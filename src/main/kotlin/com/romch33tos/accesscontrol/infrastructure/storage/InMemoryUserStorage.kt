package com.romch33tos.accesscontrol.infrastructure.storage

import com.romch33tos.accesscontrol.domain.model.User

class InMemoryUserStorage(private val users: List<User>) {
  fun findAll(): List<User> = users
  fun findByLogin(login: String): User? = users.firstOrNull { it.login == login }
}
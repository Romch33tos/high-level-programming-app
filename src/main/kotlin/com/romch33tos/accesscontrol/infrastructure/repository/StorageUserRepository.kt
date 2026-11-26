package com.romch33tos.accesscontrol.infrastructure.repository

import com.romch33tos.accesscontrol.domain.model.User
import com.romch33tos.accesscontrol.domain.repository.UserRepository
import com.romch33tos.accesscontrol.domain.service.PasswordHasher
import com.romch33tos.accesscontrol.infrastructure.storage.InMemoryUserStorage
import java.security.MessageDigest

class StorageUserRepository(
  private val storage: InMemoryUserStorage,
  private val hasher: PasswordHasher
) : UserRepository {
  override fun findUser(login: String): User? = storage.findByLogin(login)

  override fun verifyPassword(user: User, password: String): Boolean {
    val actual = hasher.hash(password, user.salt.content())
    return MessageDigest.isEqual(actual, user.passwordHash.toByteArray())
  }
}
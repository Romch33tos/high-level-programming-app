package com.romch33tos.accesscontrol.application.auth

import com.romch33tos.accesscontrol.application.fakes.FakeUserRepository
import com.romch33tos.accesscontrol.application.dto.AccessRequest
import com.romch33tos.accesscontrol.domain.model.ByteArrayValue
import com.romch33tos.accesscontrol.domain.model.PasswordHash
import com.romch33tos.accesscontrol.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DefaultAuthenticatorTest {

  private val alice = User(
    login = "alice",
    salt = ByteArrayValue(byteArrayOf(1, 2, 3)),
    passwordHash = PasswordHash(byteArrayOf(4, 5, 6))
  )

  private fun request(login: String = "alice", password: String = "secret") =
    AccessRequest(login, password, "read", "A", 1)

  @Test
  fun `shouldReturnBadLoginWhenUserDoesNotExist`() {
    val repo = FakeUserRepository(users = emptyMap())
    val auth = DefaultAuthenticator(repo)
    assertEquals(AuthResult.BadLogin, auth.authenticate(request()))
  }

  @Test
  fun `shouldReturnBadPasswordWhenPasswordIsIncorrect`() {
    val repo = FakeUserRepository(
      users = mapOf("alice" to alice),
      passwords = mapOf("alice" to "correct")
    )
    val auth = DefaultAuthenticator(repo)
    assertEquals(AuthResult.BadPassword, auth.authenticate(request(password = "wrong")))
  }

  @Test
  fun `shouldReturnSuccessWithUserWhenPasswordIsCorrect`() {
    val repo = FakeUserRepository(
      users = mapOf("alice" to alice),
      passwords = mapOf("alice" to "correct")
    )
    val auth = DefaultAuthenticator(repo)
    val result = auth.authenticate(request(password = "correct"))
    assertTrue(result is AuthResult.Success)
    assertEquals(alice, (result as AuthResult.Success).user)
  }
}

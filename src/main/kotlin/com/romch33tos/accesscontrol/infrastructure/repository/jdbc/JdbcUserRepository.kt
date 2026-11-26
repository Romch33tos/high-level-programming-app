package com.romch33tos.accesscontrol.infrastructure.repository.jdbc

import com.romch33tos.accesscontrol.domain.model.ByteArrayValue
import com.romch33tos.accesscontrol.domain.model.PasswordHash
import com.romch33tos.accesscontrol.domain.model.User
import com.romch33tos.accesscontrol.domain.repository.UserRepository
import com.romch33tos.accesscontrol.domain.service.PasswordHasher
import com.romch33tos.accesscontrol.infrastructure.utils.hexToBytes
import org.springframework.stereotype.Repository
import java.security.MessageDigest
import java.sql.SQLException
import javax.sql.DataSource

@Repository
class JdbcUserRepository(
  private val dataSource: DataSource,
  private val hasher: PasswordHasher
) : UserRepository {

  override fun findUser(login: String): User? {
    val sql = "SELECT login, salt_hex, password_hash_hex FROM users WHERE login = ?"
    try {
      dataSource.connection.use { conn ->
        conn.prepareStatement(sql).use { stmt ->
          stmt.setString(1, login)
          stmt.executeQuery().use { rs ->
            if (rs.next()) {
              val salt = rs.getString("salt_hex").hexToBytes()
              val hash = rs.getString("password_hash_hex").hexToBytes()
              return User(
                login = rs.getString("login"),
                salt = ByteArrayValue(salt),
                passwordHash = PasswordHash(hash)
              )
            }
          }
        }
      }
    } catch (e: SQLException) {
      throw DatabaseQueryException("Ошибка SQL-запроса при поиске пользователя", e)
    }
    return null
  }

  override fun verifyPassword(user: User, password: String): Boolean {
    val actual = hasher.hash(password, user.salt.content())
    return MessageDigest.isEqual(actual, user.passwordHash.toByteArray())
  }
}
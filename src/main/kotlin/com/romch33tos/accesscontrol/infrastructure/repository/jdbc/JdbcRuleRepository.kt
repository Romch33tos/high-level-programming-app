package com.romch33tos.accesscontrol.infrastructure.repository.jdbc

import com.romch33tos.accesscontrol.domain.model.AccessRule
import com.romch33tos.accesscontrol.domain.model.ResourcePath
import com.romch33tos.accesscontrol.domain.repository.RuleRepository
import com.romch33tos.accesscontrol.domain.service.EffectiveRuleResolver
import org.springframework.stereotype.Repository
import java.sql.SQLException
import javax.sql.DataSource

@Repository
class JdbcRuleRepository(
  private val dataSource: DataSource,
  private val resolver: EffectiveRuleResolver
) : RuleRepository {

  override fun findEffectiveRule(login: String, resourcePath: String): AccessRule? {
    val path = ResourcePath.parse(resourcePath) ?: return null
    val rules = mutableListOf<AccessRule>()

    val sql = "SELECT login, resource_path, can_read, can_write, can_execute FROM access_rules WHERE login = ?"
    try {
      dataSource.connection.use { conn ->
        conn.prepareStatement(sql).use { stmt ->
          stmt.setString(1, login)
          stmt.executeQuery().use { rs ->
            while (rs.next()) {
              rules.add(
                AccessRule(
                  login = rs.getString("login"),
                  resourcePath = rs.getString("resource_path"),
                  canRead = rs.getBoolean("can_read"),
                  canWrite = rs.getBoolean("can_write"),
                  canExecute = rs.getBoolean("can_execute")
                )
              )
            }
          }
        }
      }
    } catch (e: SQLException) {
      throw DatabaseQueryException("Ошибка SQL-запроса при поиске правил", e)
    }

    return resolver.resolve(rules, login, path)
  }
}
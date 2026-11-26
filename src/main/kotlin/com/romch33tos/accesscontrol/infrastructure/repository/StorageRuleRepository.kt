package com.romch33tos.accesscontrol.infrastructure.repository

import com.romch33tos.accesscontrol.domain.model.AccessRule
import com.romch33tos.accesscontrol.domain.model.ResourcePath
import com.romch33tos.accesscontrol.domain.repository.RuleRepository
import com.romch33tos.accesscontrol.domain.service.EffectiveRuleResolver
import com.romch33tos.accesscontrol.infrastructure.storage.InMemoryRuleStorage

class StorageRuleRepository(
  private val storage: InMemoryRuleStorage,
  private val resolver: EffectiveRuleResolver
) : RuleRepository {
  override fun findEffectiveRule(login: String, resourcePath: String): AccessRule? {
    val path = ResourcePath.parse(resourcePath) ?: return null
    return resolver.resolve(storage.findAll(), login, path)
  }
}
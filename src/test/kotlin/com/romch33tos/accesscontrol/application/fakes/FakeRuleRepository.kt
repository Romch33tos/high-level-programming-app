package com.romch33tos.accesscontrol.application.fakes

import com.romch33tos.accesscontrol.domain.model.AccessRule
import com.romch33tos.accesscontrol.domain.repository.RuleRepository

class FakeRuleRepository(
  private val rules: Map<Pair<String, String>, AccessRule> = emptyMap()
) : RuleRepository {
  override fun findEffectiveRule(login: String, resourcePath: String): AccessRule? =
    rules[login to resourcePath]
}

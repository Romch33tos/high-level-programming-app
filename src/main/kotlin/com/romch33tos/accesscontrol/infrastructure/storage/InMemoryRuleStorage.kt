package com.romch33tos.accesscontrol.infrastructure.storage

import com.romch33tos.accesscontrol.domain.model.AccessRule

class InMemoryRuleStorage(private val rules: List<AccessRule>) {
  fun findAll(): List<AccessRule> = rules
}
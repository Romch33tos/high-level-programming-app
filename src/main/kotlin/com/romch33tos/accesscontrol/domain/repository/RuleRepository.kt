package com.romch33tos.accesscontrol.domain.repository

import com.romch33tos.accesscontrol.domain.model.AccessRule

interface RuleRepository {
  fun findEffectiveRule(login: String, resourcePath: String): AccessRule?
}
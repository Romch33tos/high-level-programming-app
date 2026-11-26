package com.romch33tos.accesscontrol.application.fakes

import com.romch33tos.accesscontrol.domain.model.Resource
import com.romch33tos.accesscontrol.domain.repository.ResourceRepository

class FakeResourceRepository(
  private val resources: Map<String, Resource> = emptyMap()
) : ResourceRepository {
  override fun findResource(path: String): Resource? = resources[path]
}

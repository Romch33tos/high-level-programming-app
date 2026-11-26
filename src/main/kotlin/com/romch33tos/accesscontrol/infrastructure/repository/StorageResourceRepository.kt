package com.romch33tos.accesscontrol.infrastructure.repository

import com.romch33tos.accesscontrol.domain.model.Resource
import com.romch33tos.accesscontrol.domain.repository.ResourceRepository
import com.romch33tos.accesscontrol.infrastructure.storage.InMemoryResourceStorage

class StorageResourceRepository(
  private val storage: InMemoryResourceStorage
) : ResourceRepository {
  override fun findResource(path: String): Resource? = storage.findByPath(path)
}
package com.romch33tos.accesscontrol.infrastructure.storage

import com.romch33tos.accesscontrol.domain.model.Resource

class InMemoryResourceStorage(private val resources: List<Resource>) {
  fun findAll(): List<Resource> = resources
  fun findByPath(path: String): Resource? = resources.firstOrNull { it.fullPath == path }
}
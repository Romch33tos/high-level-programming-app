package com.romch33tos.accesscontrol.domain.repository

import com.romch33tos.accesscontrol.domain.model.Resource

interface ResourceRepository {
  fun findResource(path: String): Resource?
}
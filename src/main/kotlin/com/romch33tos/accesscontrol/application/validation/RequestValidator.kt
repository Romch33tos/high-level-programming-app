package com.romch33tos.accesscontrol.application.validation

import com.romch33tos.accesscontrol.application.dto.AccessRequest
import com.romch33tos.accesscontrol.application.dto.AccessResult

interface RequestValidator {
  fun validate(request: AccessRequest): AccessResult?
}
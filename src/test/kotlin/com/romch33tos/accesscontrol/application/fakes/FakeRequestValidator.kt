package com.romch33tos.accesscontrol.application.fakes

import com.romch33tos.accesscontrol.application.dto.AccessResult
import com.romch33tos.accesscontrol.application.dto.AccessRequest
import com.romch33tos.accesscontrol.application.validation.RequestValidator

class FakeRequestValidator(
  var result: AccessResult? = null
) : RequestValidator {
  override fun validate(request: AccessRequest): AccessResult? = result
}

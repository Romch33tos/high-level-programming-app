package com.romch33tos.accesscontrol.application

import com.romch33tos.accesscontrol.application.auth.AuthResult
import com.romch33tos.accesscontrol.application.auth.Authenticator
import com.romch33tos.accesscontrol.application.dto.AccessRequest
import com.romch33tos.accesscontrol.application.dto.AccessResult
import com.romch33tos.accesscontrol.application.validation.RequestValidator
import com.romch33tos.accesscontrol.domain.model.Action
import com.romch33tos.accesscontrol.domain.repository.ResourceRepository
import com.romch33tos.accesscontrol.domain.repository.RuleRepository
import org.springframework.stereotype.Service

@Service
class AccessService(
  private val validator: RequestValidator,
  private val authenticator: Authenticator,
  private val resources: ResourceRepository,
  private val rules: RuleRepository
) {
  fun check(request: AccessRequest): AccessResult {
    validator.validate(request)?.let { return it }

    val user = when (val authResult = authenticator.authenticate(request)) {
      is AuthResult.Success -> authResult.user
      AuthResult.BadLogin -> return AccessResult.BadLogin
      AuthResult.BadPassword -> return AccessResult.BadPassword
    }

    val action = Action.from(request.action) ?: return AccessResult.UnknownAction

    val resource = resources.findResource(request.resourcePath) ?: return AccessResult.NoResource
    val rule = rules.findEffectiveRule(request.login, request.resourcePath) ?: return AccessResult.NoAccess

    if (!action.isPermittedBy(rule)) return AccessResult.NoAccess
    if (request.volume > resource.maxVolume) return AccessResult.VolumeExceeded

    return AccessResult.Success
  }
}
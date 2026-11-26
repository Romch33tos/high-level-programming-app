package com.romch33tos.accesscontrol.application.fakes

import com.romch33tos.accesscontrol.application.auth.AuthResult
import com.romch33tos.accesscontrol.application.auth.Authenticator
import com.romch33tos.accesscontrol.application.dto.AccessRequest

class FakeAuthenticator(
  var result: AuthResult = AuthResult.BadLogin
) : Authenticator {
  override fun authenticate(request: AccessRequest): AuthResult = result
}

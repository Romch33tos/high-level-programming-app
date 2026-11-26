package com.romch33tos.accesscontrol.application.auth

import com.romch33tos.accesscontrol.domain.model.User

sealed interface AuthResult {
  data class Success(val user: User) : AuthResult
  data object BadLogin : AuthResult
  data object BadPassword : AuthResult
}
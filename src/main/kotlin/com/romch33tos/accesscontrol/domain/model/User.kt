package com.romch33tos.accesscontrol.domain.model

data class User(
  val login: String,
  val salt: ByteArrayValue,
  val passwordHash: PasswordHash
)
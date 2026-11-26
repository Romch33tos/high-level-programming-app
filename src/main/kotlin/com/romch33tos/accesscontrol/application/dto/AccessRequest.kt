package com.romch33tos.accesscontrol.application.dto

data class AccessRequest(
  val login: String,
  val password: String,
  val action: String,
  val resourcePath: String,
  val volume: Int
)
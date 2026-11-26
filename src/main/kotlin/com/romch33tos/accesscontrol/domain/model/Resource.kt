package com.romch33tos.accesscontrol.domain.model

data class Resource(
  val name: String,
  val parentPath: String,
  val maxVolume: Int
) {
  val fullPath: String get() = if (parentPath.isEmpty()) name else "$parentPath.$name"
}
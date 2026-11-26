package com.romch33tos.accesscontrol

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AccessControlApplication

fun main(args: Array<String>) {
  SpringApplication.run(AccessControlApplication::class.java, *args)
}
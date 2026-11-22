package infrastructure.validation

import org.springframework.stereotype.Component

@Component
data class ValidationRules(
  val namePattern: Regex = Regex("^[A-Za-z0-9_]{1,20}$"),
  val minVolume: Int = 1
)

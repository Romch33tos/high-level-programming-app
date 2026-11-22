package domain.service

import domain.model.AccessRule
import domain.model.ResourcePath
import org.springframework.stereotype.Component

@Component
class EffectiveRuleResolver {
  fun resolve(rules: List<AccessRule>, login: String, path: ResourcePath): AccessRule? {
    val segments = path.segments
    for (length in segments.size downTo 1) {
      val candidate = segments.subList(0, length).joinToString(".")
      rules.firstOrNull { it.login == login && it.resourcePath == candidate }
        ?.let { return it }
    }
    return null
  }
}

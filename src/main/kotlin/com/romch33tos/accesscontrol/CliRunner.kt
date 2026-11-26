package com.romch33tos.accesscontrol

import com.romch33tos.accesscontrol.application.AccessService
import com.romch33tos.accesscontrol.application.dto.AccessResult
import com.romch33tos.accesscontrol.infrastructure.repository.jdbc.DatabaseConnectionException
import com.romch33tos.accesscontrol.infrastructure.repository.jdbc.DatabaseQueryException
import com.romch33tos.accesscontrol.presentation.cli.AccessResultMapper
import com.romch33tos.accesscontrol.presentation.cli.CliParser
import com.romch33tos.accesscontrol.presentation.cli.CliRequestMapper
import com.romch33tos.accesscontrol.presentation.cli.ExitCode
import com.romch33tos.accesscontrol.presentation.cli.HelpPrinter
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class CliRunner(
  private val parser: CliParser,
  private val mapper: CliRequestMapper,
  private val service: AccessService
) : CommandLineRunner {

  private val log = LoggerFactory.getLogger(CliRunner::class.java)

  override fun run(vararg args: String) {
    val commandLine = System.getProperty("sun.java.command") ?: ""
    val parts = commandLine.split(" ").filter { it.isNotBlank() }
    
    val arguments = if (parts.size > 1 && parts[0].endsWith(".jar")) {
      parts.drop(1)
    } else {
      args.toList()
    }

    if (arguments.isEmpty() || arguments.contains("-h") || arguments.contains("--help")) {
      log.info("Requested help")
      HelpPrinter.print()
      exitProcess(ExitCode.HELP.code)
    }

    val argsMap = parser.parse(arguments.toTypedArray())
    if (argsMap == null) {
      log.warn("Failed to parse CLI arguments: $arguments")
      HelpPrinter.print()
      exitProcess(ExitCode.BAD_FORMAT.code)
    }

    val request = mapper.map(argsMap)
    if (request == null) {
      log.warn("Failed to map CLI arguments to request")
      HelpPrinter.print()
      exitProcess(ExitCode.BAD_FORMAT.code)
    }

    val result: AccessResult = try {
      service.check(request)
    } catch (e: DatabaseConnectionException) {
      log.error("Database connection error", e)
      exitProcess(ExitCode.DB_CONNECTION_ERROR.code)
    } catch (e: DatabaseQueryException) {
      log.error("Database query error", e)
      exitProcess(ExitCode.DB_QUERY_ERROR.code)
    }

    log.info("Access check finished with result: {}", result)
    exitProcess(AccessResultMapper.toExitCode(result).code)
  }
}

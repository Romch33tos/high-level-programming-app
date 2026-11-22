package com.romch33tos.accesscontrol

import application.AccessService
import application.dto.AccessResult
import infrastructure.repository.jdbc.DatabaseConnectionException
import infrastructure.repository.jdbc.DatabaseQueryException
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import presentation.cli.AccessResultMapper
import presentation.cli.CliParser
import presentation.cli.CliRequestMapper
import presentation.cli.ExitCode
import presentation.cli.HelpPrinter
import kotlin.system.exitProcess

@Component
class CliRunner(
  private val parser: CliParser,
  private val mapper: CliRequestMapper,
  private val service: AccessService
) : CommandLineRunner {

  private val log = LoggerFactory.getLogger(CliRunner::class.java)

  override fun run(vararg args: String) {
    val arguments = args.toList()

    if (arguments.isEmpty() || arguments.contains("-h") || arguments.contains("--help")) {
      log.info("Requested help")
      HelpPrinter.print()
      exitProcess(ExitCode.HELP.code)
    }

    val argsMap = parser.parse(arguments.toTypedArray())
    if (argsMap == null) {
      log.warn("Failed to parse CLI arguments")
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

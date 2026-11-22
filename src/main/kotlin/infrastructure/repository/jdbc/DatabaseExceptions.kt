package infrastructure.repository.jdbc

class DatabaseConnectionException(message: String, cause: Throwable) : RuntimeException(message, cause)
class DatabaseQueryException(message: String, cause: Throwable) : RuntimeException(message, cause)

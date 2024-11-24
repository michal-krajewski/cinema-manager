package pl.byteit.cinemamanager.http

abstract class ApplicationClientException(message: String?) : RuntimeException(message)
class NotFoundException(message: String?) : ApplicationClientException(message)
class BadRequestException(message: String?) : ApplicationClientException(message)
class UnauthorizedException(message: String?) : ApplicationClientException(message)
class ForbiddenException(message: String?) : ApplicationClientException(message)
class UnhandledApplicationException(message: String?) : ApplicationClientException(message)


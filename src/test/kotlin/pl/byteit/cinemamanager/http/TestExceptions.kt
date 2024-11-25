package pl.byteit.cinemamanager.http

abstract class ApplicationClientException(message: String?) : RuntimeException(message)
class NotFoundResponseException(message: String?) : ApplicationClientException(message)
class BadRequestResponseException(message: String?) : ApplicationClientException(message)
class UnauthorizedResponseException(message: String?) : ApplicationClientException(message)
class ForbiddenResponseException(message: String?) : ApplicationClientException(message)
class UnhandledApplicationException(message: String?) : ApplicationClientException(message)


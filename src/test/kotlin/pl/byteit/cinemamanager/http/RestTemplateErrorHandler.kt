package pl.byteit.cinemamanager.http

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class RestTemplateErrorHandler : ResponseErrorHandler {

    override fun hasError(response: ClientHttpResponse): Boolean {
        return !response.statusCode.is2xxSuccessful
    }

    override fun handleError(response: ClientHttpResponse) {
        throw when (response.statusCode) {
            HttpStatus.BAD_REQUEST -> BadRequestResponseException(response.body.toString())
            HttpStatus.UNAUTHORIZED -> UnauthorizedResponseException(response.body.toString())
            HttpStatus.FORBIDDEN -> ForbiddenResponseException(response.body.toString())
            HttpStatus.NOT_FOUND -> NotFoundResponseException(response.body.toString())
            else -> UnhandledApplicationException("Status code ${response.statusCode} Body: ${response.body}")
        }
    }

}


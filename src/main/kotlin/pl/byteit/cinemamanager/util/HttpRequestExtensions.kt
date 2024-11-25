package pl.byteit.cinemamanager.util

import jakarta.servlet.http.HttpServletRequest
import java.nio.charset.StandardCharsets


fun HttpServletRequest.getBody(): String =
    String(inputStream.readAllBytes(), StandardCharsets.UTF_8)




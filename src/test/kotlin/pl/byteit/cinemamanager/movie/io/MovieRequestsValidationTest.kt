package pl.byteit.cinemamanager.movie.io

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class MovieRequestsValidationTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 6, Int.MIN_VALUE, Int.MAX_VALUE])
    fun `Should prevent score values outside 1-5`(score: Int) {
        assertThatThrownBy { MovieScoreRequest(score) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0.0", "-0.1"])
    fun `Should prevent non positive values`(price: String) {
        assertThatThrownBy { SetTicketPriceRequest(BigDecimal(price)) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

}

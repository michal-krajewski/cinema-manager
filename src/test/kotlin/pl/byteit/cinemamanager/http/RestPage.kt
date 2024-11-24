package pl.byteit.cinemamanager.http

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@JsonIgnoreProperties(ignoreUnknown = true)
class RestPage<T>(
    @JsonProperty("content") content: ArrayList<T>,
    @JsonProperty("number") number: Int,
    @JsonProperty("size") size: Int,
    @JsonProperty("totalElements") totalElements: Long,
    @JsonProperty("pageable") pageable: JsonNode,
    @JsonProperty("last") last: Boolean,
    @JsonProperty("totalPages") totalPages: Int,
    @JsonProperty("sort") sort: JsonNode,
    @JsonProperty("first") first: Boolean,
    @JsonProperty("numberOfElements") numberOfElements: Long,
    @JsonProperty("total") total: Long
): PageImpl<T>(content, PageRequest.of(number, size), total)


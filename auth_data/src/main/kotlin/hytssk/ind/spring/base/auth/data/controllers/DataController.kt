package hytssk.ind.spring.base.auth.data.controllers

import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DataController(
    private val dataRepository: IDataRepository,
) {
    @GetMapping("/auth/data")
    fun getData(): ResponseEntity<List<DataDto>> {
        val result = dataRepository.findAll()
            .map { it.toDto() }
            .let {
                ResponseEntity.ok(it)
            }
        return result
    }

    data class DataDto(
        val id: String,
        val flag: Boolean,
    )

    private fun DataEntity.toDto(): DataDto {
        return DataDto(
            id = id,
            flag = flag,
        )
    }
}

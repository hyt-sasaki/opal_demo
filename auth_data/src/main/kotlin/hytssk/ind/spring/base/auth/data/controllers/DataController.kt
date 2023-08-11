package hytssk.ind.spring.base.auth.data.controllers

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DataController {
    @GetMapping("/auth/data")
    fun getData(): ResponseEntity<List<DataDto>> {
        Thread.sleep(3000)
        return ResponseEntity.ok(
            listOf(
                DataDto("1", true),
                DataDto("2", false),
                DataDto("3", true),
            )
        )
    }

    data class DataDto(
        val id: String,
        val flag: Boolean,
    )
}
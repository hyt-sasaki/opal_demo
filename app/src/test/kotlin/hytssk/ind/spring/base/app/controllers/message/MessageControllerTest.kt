package hytssk.ind.spring.base.app.controllers.message

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import kotlin.test.assertContains

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {
    @Test
    fun test() {
        val result = mockMvc.perform(get("/messages/1"))
            .andReturn()
        assertContains(result.response.contentAsString, "hello")
    }
}

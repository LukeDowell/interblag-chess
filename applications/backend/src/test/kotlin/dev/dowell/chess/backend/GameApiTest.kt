package dev.dowell.chess.backend

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameApiTest {

    @Autowired
    lateinit var wac: WebApplicationContext;

    lateinit var mockMvc: MockMvc

    @BeforeAll
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun `should create and retrieve chess game`() {
        val gameId = mockMvc.post("/games/new")
            .andExpect { status { `is`(200) } }
            .andReturn()
            .response
            .contentAsString

        mockMvc.get("/games/$gameId").andExpect { status { isOk } }
    }

    @Test
    fun `should return 404 for game that doesn't exist`() {
        mockMvc.get("/games/${UUID.randomUUID()}").andExpect { status { isNotFound } }
    }
}
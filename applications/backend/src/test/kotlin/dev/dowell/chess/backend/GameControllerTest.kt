package dev.dowell.chess.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class GameControllerTest {

    @Mock
    lateinit var gameRepository: GameRepository

    private lateinit var gameController: GameController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        gameController = GameController(gameRepository)
    }

    @Test
    fun `can start a new chess game`() {
        val uuid = UUID.randomUUID().toString()
        `when`(gameRepository.createGame()).thenReturn(uuid)
        val gameCreatedResponse = gameController.createNewGame()

        assertThat(gameCreatedResponse.body).isEqualTo(uuid)
    }
}
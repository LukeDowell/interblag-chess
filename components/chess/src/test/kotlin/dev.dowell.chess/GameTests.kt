package dev.dowell.chess

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class GameTests {

    private lateinit var game: Game
    private lateinit var board: Board

    @BeforeEach
    fun setup() {
        board = Board(pieces = allPieces)
        game = Game(board = board)
    }

    @Test
    fun game_tiles_contain_piece_information() {
        val gameTiles = game.tiles()
        val gameTilePieces = gameTiles.mapNotNull { it.piece }

        assertTrue(gameTilePieces.containsAll(board.pieces))
    }

    @Test
    fun game_tiles_are_selected() {
        val leftWhiteKnightPosition = Position(x = 1, y = 0)
        game = game.copy(selectedTile = leftWhiteKnightPosition)

        val expectedSelectedTiles: List<Position> = listOf(
            leftWhiteKnightPosition up 2 left 1,
            leftWhiteKnightPosition up 2 right 1)

        val selectedTiles = game.highlightedTiles()

        selectedTiles shouldBe expectedSelectedTiles
    }
}
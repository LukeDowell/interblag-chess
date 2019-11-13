package dev.dowell.chess

import and
import shouldBe
import with
import kotlin.test.*

class MoveEngineTests {

    private lateinit var board: Board

    @BeforeTest
    fun setup() {
        board = Board()
    }

    @Test
    fun black_pawn_moves() {
        val pawn = Piece.at(x = 1, y = 6)
        board = Board() with pawn

        assertTrue(MoveEngine.possibleMoves(board, pawn).contains(Position(x = 1, y = 5)))
    }

    @Test
    fun white_pawn_moves() {
        val pawn = Piece.at(x = 1, y = 1).copy(color = Color.WHITE)
        board = Board() with pawn

        assertTrue(MoveEngine.possibleMoves(board, pawn).contains(Position(x = 1, y = 2)))
    }

    @Test
    fun pawn_can_move_twice_on_first_turn() {
        val blackPawn = Piece.at(x = 1, y = 6)
        val whitePawn = Piece.at(x = 1, y = 1).copy(color = Color.WHITE)

        board = Board() with blackPawn and whitePawn

        assertTrue(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 1, y = 4)))
        assertTrue(MoveEngine.possibleMoves(board, whitePawn).contains(Position(x = 1, y = 3)))
    }

    @Test
    fun pawn_can_only_move_once_after_first_turn() {
        val start = Position(x = 1, y = 6)
        val blackPawn = Piece.at(start)
        blackPawn.history += Move(start, start down 1)

        board = Board() with blackPawn

        assertFalse(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 1, y = 3)))
    }

    @Test
    fun pawn_can_attack_diagonally() {
        val blackPawn = Piece.at(x = 1, y = 6)
        val whitePawn = Piece.at(x = 0, y = 5).copy(color = Color.WHITE)
        board = Board() with blackPawn and whitePawn

        assertTrue(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 0, y = 5)))
    }

    @Test
    fun bishop_moves_diagonally() {
        val start = Position(x = 3, y = 3)
        val bishop = Piece(position = start, type = PieceType.BISHOP)

        board = Board() with bishop

        val expectedMoves = (1..3).flatMap {
            listOf(
                start up it left it,
                start up it right it,
                start down it left it,
                start down it right it
            )
        } + Position(x = 7, y = 7)

        MoveEngine.possibleMoves(board, bishop) shouldBe expectedMoves
    }

    @Test
    fun bishop_cant_move_through_pieces() {
        val start = Position(x = 3, y = 3)
        val bishop = Piece(position = start, type = PieceType.BISHOP)
        val blockingPiece = Piece(position = start up 1 left 1)

        board = Board() with bishop
        val blockedBoard = Board() with bishop and blockingPiece

        val unblockedCalculatedMoves = MoveEngine.possibleMoves(board, bishop)
        val blockedCalculatedMoves = MoveEngine.possibleMoves(blockedBoard, bishop)

        val blockedMoves = (1..3).map { start up it left it }

        assertTrue(unblockedCalculatedMoves.containsAll(blockedMoves))
        assertFalse(blockedCalculatedMoves.containsAll(blockedMoves))
    }

    @Test
    fun rook_can_move_laterally_and_vertically() {
        val start = Position(x = 3, y = 3)
        val rook = Piece(position = start, type = PieceType.ROOK)
        board = Board() with rook

        val lateralMoves = (0..7).map { Position(x = it, y = 3) }
        val verticalMoves = (0..7).map { Position(x = 3, y = it) }
        val allMoves = (lateralMoves + verticalMoves).filter { it != start }

        assertTrue(MoveEngine.possibleMoves(board, rook).containsAll(allMoves))
    }

    @Test
    fun rook_cant_move_through_pieces() {
        val start = Position(x = 3, y = 3)
        val blockedPosition = Position(x = 1, y = 3)

        val rook = Piece(position = start, type = PieceType.ROOK)
        val blockedPiece = Piece.at(blockedPosition)

        board = Board() with rook and blockedPiece

        val blockedMoves = listOf(Position(x = 1, y = 3), Position(x = 0, y = 3))

        assertFalse(MoveEngine.possibleMoves(board, rook).containsAll(blockedMoves))
    }

    @Test
    fun rook_does_not_move_through_pieces() {
        val start = Position(x = 3, y = 3)
        val rook = Piece(position = start, type = PieceType.ROOK)
        board = Board() with rook
    }

    @Test
    fun queen_can_move_laterally_and_vertically_and_diagonally() {
        val start = Position(x = 3, y = 3)
        val queen = Piece(position = start, type = PieceType.QUEEN)
        board = Board() with queen

        val lateralMoves = (0..7).map { Position(x = it, y = 3) }
        val verticalMoves = (0..7).map { Position(x = 3, y = it) }
        val diagonals = (1..3).flatMap {
            listOf(
                start up it left it,
                start up it right it,
                start down it left it,
                start down it right it
            )
        }
        val allMoves = (lateralMoves + verticalMoves + diagonals).filter { it != start }

        assertTrue(MoveEngine.possibleMoves(board, queen).containsAll(allMoves))
    }

    @Test
    fun king_can_move_in_any_direction_once() {
        val start = Position(x = 3, y = 3)
        val king = Piece(position = start, type = PieceType.KING)
        board = Board() with king

        val expectedMoves = listOf(
            start left 1,
            start left 1 up 1,
            start up 1,
            start up 1 right 1,
            start right 1,
            start down 1 right 1,
            start down 1,
            start down 1 left 1
        )

        assertTrue(MoveEngine.possibleMoves(board, king).containsAll(expectedMoves))
        assertEquals(expectedMoves.size, MoveEngine.possibleMoves(board, king).size)
    }

    @Test
    fun knight_can_move() {
        val start = Position(x = 3, y = 3)
        val knight = Piece(position = start, type = PieceType.KNIGHT)

        board = Board() with knight

        val expectedMoves = listOf(
            start up 2 left 1,
            start up 2 right 1,
            start left 2 up 1,
            start left 2 down 1,
            start down 2 left 1,
            start down 2 right 1,
            start right 2 down 1,
            start right 2 up 1
        )

        assertTrue(MoveEngine.possibleMoves(board, knight).containsAll(expectedMoves))
    }

    @Test
    fun knight_can_take_opponent_and_avoids_friend() {
        val start = Position(x = 3, y = 3)
        val enemyPosition = start up 2 left 1
        val friendlyPosition = start up 2 right 1
        val knight = Piece(position = start, color = Color.WHITE, type = PieceType.KNIGHT)
        val friend = Piece(position = friendlyPosition, color = Color.WHITE)
        val enemy = Piece.at(enemyPosition)

        board = Board() with knight and enemy and friend

        val calculatedMoves = MoveEngine.possibleMoves(board, knight)

        assertTrue(calculatedMoves.contains(enemyPosition))
        assertFalse(calculatedMoves.contains(friendlyPosition))
    }
}
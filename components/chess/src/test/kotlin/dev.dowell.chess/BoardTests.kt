package dev.dowell.chess

import shouldBe
import kotlin.test.Test
import kotlin.test.assertEquals

class BoardTests {
    @Test
    fun placed_piece_should_exist() {
        val board = Board() with Piece.at(x = 0, y = 0)

        assertEquals(1, board.pieces.size)
    }

    @Test
    fun can_move_piece() {
        val start = Position(x = 0, y = 0)
        val dest = Position(x = 0, y = 1)
        val board = Board() with Piece.at(start)
        board.movePiece(start, dest)

        board shouldBe (Board() with Piece.at(dest))
    }

    @Test
    fun wont_move_invalid_piece() {
        val pieceOnePos = Position(x = 1, y = 1)
        val pieceTwoPos = Position(x = 2, y = 2)
        val pieceOne = Piece.at(pieceOnePos)
        val pieceTwo = Piece.at(pieceTwoPos)
        val board = Board() with pieceOne and pieceTwo

        board.movePiece(pieceOnePos, pieceTwoPos)

        board shouldBe (Board() with Piece.at(pieceOnePos) and Piece.at(pieceTwoPos))
    }
}
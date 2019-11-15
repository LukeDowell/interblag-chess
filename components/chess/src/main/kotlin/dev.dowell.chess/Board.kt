package dev.dowell.chess

data class Board(var pieces: List<Piece> = listOf()) {
    override fun toString(): String {
        return super.toString()
    }
}

fun Board.movePiece(from: Position, to: Position): Unit {
    if (this.pieces.any { it.position == to }) {
        return
    }

    if (this.pieces.none { it.position == from }) {
        return
    }

    this.pieces = pieces.map {
        if (it.position == from) {
            it.history += Move(from, to)
            it.copy(position = to)
        } else it
    }
}

fun Board.pieceAt(position: Position): Piece? = this.pieces.find { it.position == position }

enum class Color { WHITE, BLACK; }
enum class PieceType { PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING }

data class Position(val x: Int, val y: Int)

infix fun Position.up(i: Int): Position = Position(this.x, this.y + i)
infix fun Position.down(i: Int): Position = Position(this.x, this.y - i)
infix fun Position.left(i: Int): Position = Position(this.x + i, this.y)
infix fun Position.right(i: Int): Position = Position(this.x - i, this.y)

data class Move(val from: Position, val to: Position)

data class Piece(val position: Position, val color: Color = Color.BLACK, val type: PieceType = PieceType.PAWN) {
    companion object {
        fun at(x: Int, y: Int): Piece = Piece(position = Position(x, y))
        fun at(position: Position): Piece = Piece(position = position)
    }

    var history: List<Move> = listOf()
}

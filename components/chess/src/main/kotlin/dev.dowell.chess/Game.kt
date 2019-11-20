package dev.dowell.chess

data class Game(val board: Board = Board(), val selectedTile: Position? = null)
data class ChessTile(val position: Position, val piece: Piece? = null, val isSelected: Boolean = false)

fun Game.highlightedTiles(): List<Position> {
    val pieceAtSelection: Piece? = selectedTile?.let { board.pieceAt(it) }
    return pieceAtSelection?.let { MoveEngine.possibleMoves(board, it) }.orEmpty()
}

fun Game.tiles(): List<ChessTile> {
    val highlightedTiles = highlightedTiles()

    return (0..7).map { y ->
        (0..7).map { x ->
            Position(x = x, y = y)
        }.map { ChessTile(position = it, piece = board.pieceAt(it), isSelected = highlightedTiles.contains(it)) }
    }.flatten()
}

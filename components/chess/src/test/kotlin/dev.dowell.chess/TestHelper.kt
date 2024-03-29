package dev.dowell.chess

import org.junit.jupiter.api.Assertions.assertEquals

infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)

infix fun List<Position>.shouldBe(other: List<Position>): Unit {
    val positionComparator: Comparator<Position> = compareBy({it.y}, {it.x})
    assertEquals(other.sortedWith(positionComparator), this.sortedWith(positionComparator))
}

infix fun Board.and(piece: Piece): Board {
    this.pieces += piece; return this
}

infix fun Board.with(piece: Piece): Board {
    this.pieces += piece; return this
}
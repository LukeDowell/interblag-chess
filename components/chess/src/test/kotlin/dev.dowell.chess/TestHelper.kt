import dev.dowell.chess.Board
import dev.dowell.chess.Position
import kotlin.test.assertEquals

infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)
infix fun List<Position>.shouldBe(other: List<Position>): Unit {
    val positionComparator: Comparator<Position> = compareBy({it.y}, {it.x})
    assertEquals(other.sortedWith(positionComparator), this.sortedWith(positionComparator))
}
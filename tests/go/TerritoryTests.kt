package go

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

object TerritoryTests {
    val game = GoGame()
    val territoryFinder = TerritoryFinder(game)
    var territories = mapOf<BoardPosition, StoneColor>()

    @Test fun territories_notSurrounded_noMansLand() {
        givenBoard("""
                __B_
                _B__
                __B_
        """)
        whenPlacingStoneAt(BoardPosition(2, 2))
        thenTheTerritoryIs(StoneColor.black) // empty
    }

    @Test fun territories_singleTileSurroundedByBlack_blackTerritory() {
        givenBoard("""
                __B__
                _B_B_
                __B__
        """)
        whenPlacingStoneAt(BoardPosition(2, 2))
        thenTheTerritoryIs(StoneColor.black, BoardPosition(2, 1))
    }

    @Test fun territories_twoVerticalTilesSurroundedByBlack_blackTerritory() {
        givenBoard("""
                __B__
                _B_B_
                _B_B_
                __B__
        """)
        whenPlacingStoneAt(BoardPosition(2, 3))
        thenTheTerritoryIs(StoneColor.black,
                BoardPosition(2, 1),
                BoardPosition(2, 2))
    }

    @Test fun territories_arbitraryShapeSurroundedByBlack_blackTerritory() {
        givenBoard("""
                __B____
                _B_BB__
                _B___B_
                _B_BB__
                __B____
        """)
        whenPlacingStoneAt(BoardPosition(2, 4))
        thenTheTerritoryIs(StoneColor.black,
                BoardPosition(2, 1),
                BoardPosition(2, 2),  BoardPosition(3, 2),  BoardPosition(4, 2),
                BoardPosition(2, 3))
    }

    @Test fun territories_singleTileSurroundedByWhite_whiteTerritory() {
        givenBoard("""
                __W__
                _W_W_
                __W__
        """)
        whenPlacingStoneAt(BoardPosition(2, 2))
        thenTheTerritoryIs(StoneColor.white, BoardPosition(2, 1))
    }

    private fun givenBoard(layout: String) {
        GoBoardDSL(game).setup(layout)
    }

    private fun whenPlacingStoneAt(position: BoardPosition) {
        territories = territoryFinder.territoriesNear(position)
    }

    private fun thenTheTerritoryIs(color: StoneColor, vararg expected: BoardPosition) {
        val actual = territories
                .filter { it.value == color }
                .map { it.key }
                .sortedBy { (expected.indexOf(it) + 99) % 99 } // same order, right?
        assertEquals(expected.toList(), actual)
    }
}

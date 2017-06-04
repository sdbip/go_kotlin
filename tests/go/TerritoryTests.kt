package go

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

object TerritoryTests {
    var territorialMap = TerritorialMap(Board())

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

    @Test fun territories_leftEdgeBorder_isTerritory() {
        givenBoard("""
                W__
                _W_
                W__
                ___
        """)
        whenPlacingStoneAt(BoardPosition(0, 2))
        thenTheTerritoryIs(StoneColor.white, BoardPosition(0, 1))
    }

    @Test fun territories_noCycle_noTerritory() {
        givenBoard("""
                W___
                _W__
                ____
                ____
        """)
        whenPlacingStoneAt(BoardPosition(1, 1))
        thenTheTerritoryIs(StoneColor.white) // empty
    }

    @Test fun territories_topEdgeBorder_isTerritory() {
        givenBoard("""
                W_W_
                _W__
                ____
                ____
        """)
        whenPlacingStoneAt(BoardPosition(1, 1))
        thenTheTerritoryIs(StoneColor.white, BoardPosition(1, 0))
    }

    @Test fun territories_rightEdgeBorder_isTerritory() {
        givenBoard("""
                ___W
                __W_
                ___W
                ____
        """)
        whenPlacingStoneAt(BoardPosition(x = 2, y = 1))
        thenTheTerritoryIs(StoneColor.white, BoardPosition(x = 3, y = 1))
    }

    @Test fun territories_bottomEdgeBorder_isTerritory() {
        givenBoard("""
                ____
                ____
                _W__
                W_W_
        """)
        whenPlacingStoneAt(BoardPosition(1, 2))
        thenTheTerritoryIs(StoneColor.white, BoardPosition(1, 3))
    }

    private fun givenBoard(layout: String) {
        territorialMap = TerritorialMap(GoBoardDSL(layout).board())
    }

    private fun whenPlacingStoneAt(position: BoardPosition) {
        territorialMap.changeTerritories(playedPosition = position)
    }

    private fun thenTheTerritoryIs(color: StoneColor, vararg expected: BoardPosition) {
        val actual = territorialMap.territories
                .filter { it.value == color }
                .map { it.key }
                .sortedBy { (expected.indexOf(it) + 99) % 99 } // same order, right?
        assertEquals(expected.toList(), actual)
    }
}

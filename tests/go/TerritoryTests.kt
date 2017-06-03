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
        thenThereIsNoTerritoryAt(BoardPosition(2, 1))
    }

    @Test fun territories_singleTileSurroundedByBlack_blackTerritory() {
        givenBoard("""
                __B__
                _B_B_
                __B__
        """)
        whenPlacingStoneAt(BoardPosition(2, 2))
        thenTheTerritoryIs(StoneColor.black, at = BoardPosition(2, 1))
    }

    @Test fun territories_singleTileSurroundedByWhite_whiteTerritory() {
        givenBoard("""
                __W__
                _W_W_
                __W__
        """)
        whenPlacingStoneAt(BoardPosition(2, 2))
        thenTheTerritoryIs(StoneColor.white, at = BoardPosition(2, 1))
    }

    private fun givenBoard(layout: String) {
        GoBoardDSL(game).setup(layout)
    }

    private fun whenPlacingStoneAt(position: BoardPosition) {
        territories = territoryFinder.territoriesNear(position)
    }

    private fun thenTheTerritoryIs(color: StoneColor, at: BoardPosition) {
        assertEquals(color, territories[at])
    }

    private fun thenThereIsNoTerritoryAt(position: BoardPosition) {
        assertNull(territories[position])
    }
}

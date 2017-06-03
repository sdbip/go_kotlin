package go

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

object TerritoryTests {
    val game = GoGame()
    val territoryFinder = TerritoryFinder(game)

    @Test fun territories_notSurrounded_noMansLand() {
        givenBoard("""
                __B_
                _B__
                __B_
        """)

        val territories = territoryFinder.territoriesNear(BoardPosition(2, 2))

        assertNull(territories[BoardPosition(2, 1)])
    }

    @Test fun territories_singleSurroundedBlack_blackTerritory() {
        givenBoard("""
                __B__
                _B_B_
                __B__
        """)

        val territories = territoryFinder.territoriesNear(BoardPosition(2, 2))

        assertEquals(StoneColor.black, territories[BoardPosition(2, 1)])
    }

    private fun givenBoard(layout: String) {
        GoBoardDSL(game).setup(layout)
    }
}

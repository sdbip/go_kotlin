package go

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

object GamePlayTests {
    val game = GoGame()

    @Test fun removesSurroundedStoneAbove() {
        givenBoard("""
                __B__
                _BWB_
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 2, y = 2))
        thenTheStoneIsRemovedFrom(BoardPosition(x = 2, y = 1))
    }

    @Test fun removesSurroundedStoneAtEdge() {
        givenBoard("""
                B__
                WB_
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 0, y = 2))
        thenTheStoneIsRemovedFrom(BoardPosition(x = 0, y = 1))
    }

    @Test fun removesSurroundedStoneAtLeft() {
        givenBoard("""
                __B__
                _BW__
                __B__
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 3, y = 1))
        thenTheStoneIsRemovedFrom(BoardPosition(x = 2, y = 1))
    }

    @Test fun removesSurroundedStoneBelow() {
        givenBoard("""
                _____
                _BWB_
                __B__
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 2, y = 0))
        thenTheStoneIsRemovedFrom(BoardPosition(x = 2, y = 1))
    }

    @Test fun removesSurroundedStoneAtRight() {
        givenBoard("""
                __B__
                __WB_
                __B__
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 1, y = 1))
        thenTheStoneIsRemovedFrom(BoardPosition(x = 2, y = 1))
    }

    @Test fun doesNotRemoveIfNotSurrounded() {
        givenBoard("""
                __B_
                _BW_
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 2, y = 2))
        thenThereIsAWhiteStoneAt(BoardPosition(x = 2, y = 1))
    }

    @Test fun doesNotRemoveIfNotSurrounded2() {
        givenBoard("""
                _B__
                _WB_
        """)
        whenPlacingBlackStoneAt(BoardPosition(x = 1, y = 2))
        thenThereIsAWhiteStoneAt(BoardPosition(x = 1, y = 1))
    }

    private fun givenBoard(layout: String) {
        GoBoardDSL(game).setup(layout)
    }

    private fun whenPlacingBlackStoneAt(position: BoardPosition) {
        game.placeStone(StoneColor.black, position)
    }

    private fun thenTheStoneIsRemovedFrom(position: BoardPosition) {
        assertNull(game.stoneAt(position))
    }

    private fun thenThereIsAWhiteStoneAt(position: BoardPosition) {
        assertEquals(StoneColor.white, game.stoneAt(position))
    }
}

// TODO: Add a complicated test after finishing TerritoryTests

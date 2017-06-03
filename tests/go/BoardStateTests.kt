package go

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

object BoardStateTests {
    val game = GoGame()

    @Test fun arbitraryPosition_initialStateIsEmpty() {
        givenBoard("""
            __
            __
        """)
        thenThereIsNoStoneAt(BoardPosition(x = 1, y = 1))
    }

    @Test fun differentPosition_initialStateIsEmpty() {
        givenBoard("""
            __
            __
        """)
        thenThereIsNoStoneAt(BoardPosition(x = 0, y = 0))
    }

    @Test fun placedBlackStone_returnsStoneColor() {
        givenBoard("""
            __
            _B
        """)
        thenThereIsAStoneAt(BoardPosition(x = 1, y = 1), color = StoneColor.black)
    }

    @Test fun placedWhiteStone_returnsStoneColor() {
        givenBoard("""
            __
            _W
        """)
        thenThereIsAStoneAt(BoardPosition(x = 1, y = 1), color = StoneColor.white)
    }

    @Test fun placedInDifferentPosition_returnsStoneColor() {
        givenBoard("""
            W_
            __
        """)
        thenThereIsAStoneAt(BoardPosition(x = 0, y = 0), StoneColor.white)
    }

    private fun givenBoard(layout: String) {
        GoBoardDSL(game).setup(layout)
    }

    private fun thenThereIsNoStoneAt(position: BoardPosition) {
        assertNull(game.stoneAt(position))
    }

    private fun thenThereIsAStoneAt(position: BoardPosition, color: StoneColor) {
        assertEquals(color, game.stoneAt(position))
    }
}

package go

class GoBoardDSL(private val game: GoGame) {
    fun setup(layout: String) {
        val lines = layout.split("\n").map { it.trim() }.filter { it != "" }
        for ((y, line) in lines.withIndex())
            for ((x, character) in line.withIndex())
                placeStone(character, x, y)
    }

    private fun placeStone(character: Char, x: Int, y: Int) {
        when (character) {
            'B' -> game.placeStone(StoneColor.black, BoardPosition(x, y))
            'W' -> game.placeStone(StoneColor.white, BoardPosition(x, y))
        }
    }
}

package go

class GoBoardDSL(val layout: String) {
    fun board(): Board {
        val board = Board()

        fun placeStone(character: Char, x: Int, y: Int) {
            when (character) {
                'B' -> board.placeStone(StoneColor.black, BoardPosition(x, y))
                'W' -> board.placeStone(StoneColor.white, BoardPosition(x, y))
            }
        }

        val lines = layout.split("\n").map { it.trim() }.filter { it != "" }
        for ((y, line) in lines.withIndex())
            for ((x, character) in line.withIndex())
                placeStone(character, x, y)
        return board
    }
}

package go

class GoBoardDSL(val layout: String) {
    fun board(): Board {
        val board = Board()
        var max = 0

        fun placeStone(character: Char, position: BoardPosition) {
            when (character) {
                'B' -> board.placeStone(StoneColor.black, position)
                'W' -> board.placeStone(StoneColor.white, position)
            }
        }

        val lines = layout.split("\n").map { it.trim() }.filter { it != "" }
        for ((y, line) in lines.withIndex())
            for ((x, character) in line.withIndex()) {
                placeStone(character, BoardPosition(x, y))
                max = maxOf(x, y, max)
            }
        board.size = max + 1
        return board
    }
}

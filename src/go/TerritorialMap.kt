package go

class TerritorialMap(val board: Board) {
    val territories get() = mutableTerritories.toMap()
    private val mutableTerritories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition: BoardPosition) {
        val color = board.stoneAt(playedPosition)
                ?: throw IllegalArgumentException("Played position must contain a stone")

        for (neighbour in playedPosition.neighbours().filter {
            Territory(board, it).isSurroundedBy(color)
        })
            floodFill(color, from = neighbour)
    }

    private fun floodFill(color: StoneColor, from: BoardPosition) {
        filler(color).fillFrom(from)
    }

    fun filler(color: StoneColor) = object : GoGameFillerAlgorithm(board, color) {
        override fun paint(position: BoardPosition) {
            mutableTerritories[position] = color
        }

        override fun isPainted(position: BoardPosition): Boolean =
                super.isPainted(position) || mutableTerritories[position] == color
    }
}

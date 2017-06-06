package go

class TerritorialMap(val board: Board) {
    val territories get() = mutableTerritories.toMap()
    private val mutableTerritories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition: BoardPosition) {
        val finder = TerritoryFloodFillAlgorithm(board, playedPosition)
        val filler = filler(finder.playedColor)

        for (startingPosition in finder.findNewTerritories())
            filler.fillFrom(startingPosition)
    }

    fun filler(color: StoneColor) = object : GoGameFillerAlgorithm(board, color) {
        override fun paint(position: BoardPosition) {
            mutableTerritories[position] = color
        }

        override fun isPainted(position: BoardPosition): Boolean =
                super.isPainted(position) || mutableTerritories[position] == color
    }
}

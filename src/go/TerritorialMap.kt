package go

class TerritorialMap(val board: Board) {
    val territories get() = mutableTerritories.toMap()
    private val mutableTerritories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition: BoardPosition) {
        val algorithm = TerritoryFloodFillAlgorithm(board, playedPosition)

        for (startingPosition in algorithm.findNewTerritories())
            paintTerritory(startingPosition, algorithm.playedColor)
    }

    private fun paintTerritory(startingPosition: BoardPosition, color: StoneColor) {
        object : FloodFillAlgorithm<BoardPosition>() {
            override fun paint(position: BoardPosition) {
                mutableTerritories[position] = color
            }

            override fun isPainted(position: BoardPosition): Boolean =
                    !board.isInBounds(position) ||
                            board.stoneAt(position) == color ||
                            mutableTerritories[position] == color

            override fun neighboursOf(position: BoardPosition): Iterable<BoardPosition> {
                return position.neighbours()
            }
        }.fill(startingPosition)
    }
}

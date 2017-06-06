package go

class TerritorialMap(val board: Board) {
    val territories get() = mutableTerritories.toMap()
    private val mutableTerritories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition: BoardPosition) {
        val algorithm = FloodFillAlgorithm(board, playedPosition)

        for (startingPosition in algorithm.findNewTerritories())
            paintTerritory(startingPosition, algorithm.playedColor)
    }

    private fun paintTerritory(startingPosition: BoardPosition, color: StoneColor) {
        if (!board.isInBounds(startingPosition)) return
        if (board.stoneAt(startingPosition) == color) return
        if (mutableTerritories[startingPosition] == color) return

        mutableTerritories[startingPosition] = color
        for (neighbour in startingPosition.neighbours()) {
            paintTerritory(neighbour, color)
        }
    }
}

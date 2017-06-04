package go

class TerritorialMap(private val board: Board) {
    val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition : BoardPosition) {
        val color = board.stoneAt(playedPosition)!!
        val state = TerritoryFinderAlgorithm(board, color, playedPosition)

        for (startingPosition in state.findNewTerritories())
            paintTerritory(startingPosition, color)
    }

    private fun paintTerritory(startingPosition: BoardPosition, color: StoneColor) {
        if (isOutOfBounds(startingPosition)) return
        if (board.stoneAt(startingPosition) == color) return
        if (territories[startingPosition] == color) return

        territories[startingPosition] = color
        for (neighbour in neighboursOf(startingPosition)) {
            paintTerritory(neighbour, color)
        }
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private fun isOutOfBounds(position: BoardPosition) =
            position.x < 0 || position.y < 0 || position.x > 5 || position.y > 5
}

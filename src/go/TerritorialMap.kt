package go

class TerritorialMap(val board: Board) {
    val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition : BoardPosition) {
        val color = board.stoneAt(playedPosition)
                ?: throw IllegalArgumentException("Played position must contain a stone")
        val algorithm = FloodFillAlgorithm(board, color, playedPosition)

        for (startingPosition in algorithm.findNewTerritories())
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
            position.x < 0 || position.x >= board.size ||
                    position.y < 0 || position.y >= board.size
}

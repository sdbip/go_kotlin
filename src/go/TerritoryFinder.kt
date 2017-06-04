package go

class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        val color = game.stoneAt(position)!!
        val state = TerritoryFinderAlgorithm(game, color, position)

        for (startingPosition in state.findNewTerritories())
            paintTerritory(startingPosition, color)

        return territories
    }

    private fun paintTerritory(startingPosition: BoardPosition, color: StoneColor) {
        if (isOutOfBounds(startingPosition)) return
        if (game.stoneAt(startingPosition) == color) return
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

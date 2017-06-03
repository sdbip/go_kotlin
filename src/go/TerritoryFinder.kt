package go

// TODO: This is actually the accumulated state
class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        for (color in arrayOf(StoneColor.black, StoneColor.white)) {
            for (startingPosition in neighboursOf(position).filter { isSurrounded(it, color) })
                paintTerritory(startingPosition, color)
        }

        return territories
    }

    private fun paintTerritory(startingPosition: BoardPosition, color: StoneColor) {
        territories[startingPosition] = color
    }

    private fun isSurrounded(position: BoardPosition, color: StoneColor) =
            neighboursOf(position).all { it.x < 0 || game.stoneAt(it) == color }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }
}

package go

// TODO: This is actually the accumulated state
class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        for (surrounded in neighboursOf(position).filter { isSurrounded(it) }) {
            territories[surrounded] = StoneColor.black
        }
        return territories
    }

    private fun isSurrounded(position: BoardPosition) =
            neighboursOf(position).all { it.x < 0 || game.stoneAt(it) == StoneColor.black }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }
}

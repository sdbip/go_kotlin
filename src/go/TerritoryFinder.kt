package go

class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        val neighbours = Delta.unitDirections.map { position + it }
        for (surrounded in neighbours.filter { isSurrounded(it) }) {
            territories[surrounded] = StoneColor.black
        }
        return territories
    }

    private fun isSurrounded(position: BoardPosition): Boolean {
        val neighbours = Delta.unitDirections.map { position + it }
        return neighbours.all { it.x < 0 || game.stoneAt(it) == StoneColor.black }
    }
}

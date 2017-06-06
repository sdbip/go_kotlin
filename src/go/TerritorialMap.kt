package go

class TerritorialMap(val board: Board) {
    val territories get() = mutableTerritories.toMap()
    private val mutableTerritories = mutableMapOf<BoardPosition, StoneColor>()

    fun changeTerritories(playedPosition: BoardPosition) {
        val color = board.stoneAt(playedPosition)
                ?: throw IllegalArgumentException("playedPosition must contain a stone")
        val neighboringTerritories = playedPosition.neighbours()
                .mapNotNull { territoryAt(it, color) }

        for (territory in neighboringTerritories)
            add(territory)
    }

    private fun territoryAt(startingPosition: BoardPosition, boundingStones: StoneColor): Territory? =
            if (TerritoryFinder(board, boundingStones).isTerritory(startingPosition))
                Territory(startingPosition, boundingStones)
            else null

    private fun add(territory: Territory) {
        filler(territory.boundingStones).fillFrom(territory.startingPosition)
    }

    fun filler(color: StoneColor) = object : GoGameFillerAlgorithm(board, color) {
        override fun paint(position: BoardPosition) {
            mutableTerritories[position] = color
        }

        override fun isPainted(position: BoardPosition): Boolean =
                super.isPainted(position) || mutableTerritories[position] == color
    }

    private class Territory(
            val startingPosition: BoardPosition,
            val boundingStones: StoneColor)
}

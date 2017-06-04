package go

class TerritoryFinderAlgorithm(private val game: GoGame, val color: StoneColor, val playedPosition: BoardPosition) {
    private val map = mutableMapOf<BoardPosition, State>()

    fun findNewTerritories(): List<BoardPosition> {
        neighboursOf(playedPosition)
                .filter { !isKnown(it) }
                .forEach { paintTerritoryAt(it) }
        return neighboursOf(playedPosition).filter { isPossible(it) }
    }

    private fun paintTerritoryAt(currentPosition: BoardPosition) {
        if (isKnown(currentPosition)) return
        val thisState = State()
        map[currentPosition] = thisState

        fun mergeStateWith(neighbour: BoardPosition) {
            val neighbouringState = map[neighbour] ?: return
            if (!neighbouringState.isPossible)
                thisState.isPossible = false
        }

        for (neighbour in neighboursOf(currentPosition)) {
            if (isAtDisallowedEdge(neighbour)) {
                thisState.isPossible = false
                return
            }
        }

        for (neighbour in neighboursOf(currentPosition)) {
            if (neighbour.x < 0 || neighbour.y < 0 ||
                    isKnownPossible(neighbour) ||
                    game.stoneAt(neighbour) == color) continue

            paintTerritoryAt(neighbour)
            mergeStateWith(neighbour)
            if (!isKnownPossible(currentPosition)) return
        }
    }

    private fun isPossible(position: BoardPosition): Boolean {
        return map[position]?.isPossible ?: true
    }

    private fun isKnown(position: BoardPosition): Boolean {
        return map[position] != null
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private fun isAtDisallowedEdge(position: BoardPosition) =
            position.x > 5 || position.y > 5

    private fun isKnownPossible(position: BoardPosition): Boolean {
        return map[position]?.isPossible ?: false
    }

    private class State {
        var isPossible = true

        override fun toString(): String {
            return if (isPossible) "possible" else "not possible"
        }
    }
}
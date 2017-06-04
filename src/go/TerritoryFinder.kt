package go

// TODO: This is actually the accumulated state
class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        val color = game.stoneAt(position)!!
        val stateMap = mutableMapOf<BoardPosition, State>()

        for (neighbour in neighboursOf(position).filter { !stateMap.isKnown(it) }) {
            populateStateMap(neighbour, stateMap, color)
        }

        for (startingPosition in neighboursOf(position).filter { stateMap.isPossible(it) })
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

    private fun populateStateMap(currentPosition: BoardPosition, state: MutableMap<BoardPosition, State>, color: StoneColor) {
        if (state.isKnown(currentPosition)) return
        val thisState = State()
        state[currentPosition] = thisState

        fun mergeStateWith(neighbour: BoardPosition) {
            val neighbouringState = state[neighbour] ?: return
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
                    state.isKnownPossible(neighbour) ||
                    game.stoneAt(neighbour) == color) continue

            populateStateMap(neighbour, state, color)
            mergeStateWith(neighbour)
            if (!state.isKnownPossible(currentPosition)) return
        }
    }

    private fun isOutOfBounds(position: BoardPosition) =
            position.x < 0 || position.y < 0 || position.x > 5 || position.y > 5

    private fun isAtDisallowedEdge(position: BoardPosition) =
            position.x > 5 || position.y > 5

    private fun Map<BoardPosition, State>.isPossible(position: BoardPosition): Boolean {
        return this[position]?.isPossible ?: true
    }

    private fun Map<BoardPosition, State>.isKnownPossible(position: BoardPosition): Boolean {
        return this[position]?.isPossible ?: false
    }

    private fun Map<BoardPosition, State>.isKnown(position: BoardPosition): Boolean {
        return this[position] != null
    }

    private class State {
        var isPossible = true

        override fun toString(): String {
            return if (isPossible) "possible" else "not possible"
        }
    }
}

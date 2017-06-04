package go

// TODO: This is actually the accumulated state
class TerritoryFinder(private val game: GoGame) {
    private val territories = mutableMapOf<BoardPosition, StoneColor>()

    fun territoriesNear(position: BoardPosition): Map<BoardPosition, StoneColor> {
        val color = game.stoneAt(position)!!
        for (startingPosition in neighboursOf(position).filter { isSurrounded(it, color) })
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

    private fun isSurrounded(position: BoardPosition, color: StoneColor): Boolean {
        val stateMap = mutableMapOf<BoardPosition, State>()
        for (neighbour in neighboursOf(position)) {
            populateStateMap(neighbour, stateMap, color)
        }
        return stateMap.isPossible(position)
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private fun populateStateMap(currentPosition: BoardPosition, state: MutableMap<BoardPosition, State>, color: StoneColor) {

        fun mergeStateWith(neighbour: BoardPosition) {
            val neighbouringState = state[neighbour] ?: return
            if (!neighbouringState.isPossible)
                state[currentPosition]?.isPossible = false
        }

        state[currentPosition] = State()

        for (neighbour in neighboursOf(currentPosition)) {
            if (isAtDisallowedEdge(neighbour)) {
                state[currentPosition]?.isPossible = false
                return
            }
        }

        for (neighbour in neighboursOf(currentPosition)) {
            if (neighbour.x < 0 ||
                    state.isPossible(neighbour) ||
                    game.stoneAt(neighbour) == color) continue

            populateStateMap(neighbour, state, color)
            mergeStateWith(neighbour)
            if (!state.isPossible(currentPosition))
                return
        }
    }

    private fun isOutOfBounds(position: BoardPosition) =
            position.x < 0 || position.y < 0 || position.x > 5 || position.y > 5

    private fun isAtDisallowedEdge(position: BoardPosition) =
            position.y < 0 || position.x > 5 || position.y > 5

    private fun Map<BoardPosition, State>.isPossible(position: BoardPosition): Boolean {
        return this[position]?.isPossible ?: false
    }

    private class State {
        var isPossible = true
    }
}

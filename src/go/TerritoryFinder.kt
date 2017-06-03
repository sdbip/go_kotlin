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
        territories[startingPosition] = color
        for (neighbour in neighboursOf(startingPosition)) {
            if (game.stoneAt(neighbour) == color) continue
            if (territories[neighbour] == color) continue
            paintTerritory(neighbour, color)
        }
    }

    private fun isSurrounded(position: BoardPosition, color: StoneColor): Boolean {
        val stateMap = mutableMapOf<BoardPosition, State>()
        for (neighbour in neighboursOf(position)) {
            populateStateMap(neighbour, stateMap, color)
        }
        return stateMap[position] == State.possible
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private fun populateStateMap(currentPosition: BoardPosition, state: MutableMap<BoardPosition, State>, color: StoneColor) {
        for (neighbour in neighboursOf(currentPosition)) {
            if (game.isOutOfBounds(neighbour)) {
                state[currentPosition] = State.connectedToEdge
                return
            }
        }

        state[currentPosition] = State.possible
        for (neighbour in neighboursOf(currentPosition)) {
            if (state[neighbour] == State.possible ||
                    game.stoneAt(neighbour) == color) continue

            populateStateMap(neighbour, state, color)
            if (state[neighbour] == State.connectedToEdge) {
                state[currentPosition] = State.connectedToEdge
                return
            }
        }
    }
}

private fun GoGame.isOutOfBounds(position: BoardPosition) =
        position.x < 0 || position.y < 0 || position.x > 5 || position.y > 5


enum class State {
    possible, connectedToEdge
}

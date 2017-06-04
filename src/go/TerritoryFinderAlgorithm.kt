package go

class TerritoryFinderAlgorithm(private val board: Board, val color: StoneColor, val playedPosition: BoardPosition) {
    private val map = mutableMapOf<BoardPosition, Territory>()

    fun findNewTerritories(): List<BoardPosition> {
        for (neighbour in neighboursOf(playedPosition))
            expandTerritoryTo(neighbour, Territory())
        return neighboursOf(playedPosition).filter { map[it]?.isPossible ?: false }
    }

    private fun expandTerritoryTo(position: BoardPosition, territory: Territory) {
        if (!board.isInBounds(position) || board.stoneAt(position) == color) return
        if (map[position] != null) return

        map[position] = territory
        addEdgeInfo(position, territory)

        for (neighbour in neighboursOf(position))
            expandTerritoryTo(neighbour, territory)
    }

    private fun addEdgeInfo(currentPosition: BoardPosition, territory: Territory) {
        if (currentPosition.x <= 0) territory.reachesLeftEdge = true
        if (currentPosition.x >= board.size - 1) territory.reachesRightEdge = true
        if (currentPosition.y <= 0) territory.reachesTopEdge = true
        if (currentPosition.y >= board.size - 1) territory.reachesBottomEdge = true
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private class Territory {
        var reachesLeftEdge = false
        var reachesRightEdge = false
        var reachesTopEdge = false
        var reachesBottomEdge = false

        val isPossible get() = (!reachesLeftEdge || !reachesRightEdge)
                && (!reachesTopEdge || !reachesBottomEdge)
    }
}

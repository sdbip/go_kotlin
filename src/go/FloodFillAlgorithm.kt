package go

class FloodFillAlgorithm(
        private val board: Board,
        private val playedPosition: BoardPosition) {
    private val map = mutableMapOf<BoardPosition, Territory>()
    val playedColor = board.stoneAt(playedPosition)
            ?: throw IllegalArgumentException("Played position must contain a stone")

    fun findNewTerritories(): List<BoardPosition> {
        for (neighbour in neighboursOf(playedPosition))
            expandTerritoryTo(neighbour, Territory())
        return neighboursOf(playedPosition).filter { map[it]?.isSurrounded ?: false }
    }

    private fun expandTerritoryTo(position: BoardPosition, territory: Territory) {
        if (!board.isInBounds(position) || board.stoneAt(position) == playedColor) return
        if (map[position] != null) return

        map[position] = territory
        territory.addEdgeInfo(position)

        for (neighbour in neighboursOf(position))
            expandTerritoryTo(neighbour, territory)
    }

    private fun Territory.addEdgeInfo(position: BoardPosition) {
        if (position.x <= 0) reachesLeftEdge = true
        if (position.x >= board.size - 1) reachesRightEdge = true
        if (position.y <= 0) reachesTopEdge = true
        if (position.y >= board.size - 1) reachesBottomEdge = true
    }

    private fun neighboursOf(position: BoardPosition) =
            Delta.unitDirections.map { position + it }

    private class Territory {
        var reachesLeftEdge = false
        var reachesRightEdge = false
        var reachesTopEdge = false
        var reachesBottomEdge = false

        val isSurrounded get() = !(reachesLeftEdge && reachesRightEdge)
                && !(reachesTopEdge && reachesBottomEdge)
    }
}

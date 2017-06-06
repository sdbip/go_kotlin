package go

class TerritoryFloodFillAlgorithm(
        private val board: Board,
        private val playedPosition: BoardPosition) {
    private val map = mutableMapOf<BoardPosition, Territory>()
    val playedColor = board.stoneAt(playedPosition)
            ?: throw IllegalArgumentException("Played position must contain a stone")

    fun findNewTerritories(): List<BoardPosition> {
        for (neighbour in playedPosition.neighbours())
            expandTerritoryTo(neighbour, Territory())
        return playedPosition.neighbours().filter { map[it]?.isSurrounded ?: false }
    }

    private fun expandTerritoryTo(position: BoardPosition, territory: Territory) {
        object : FloodFillAlgorithm<BoardPosition>() {
            override fun paint(position: BoardPosition) {
                map[position] = territory
                territory.addEdgeInfo(position)
            }

            override fun isPainted(position: BoardPosition) =
                    !board.isInBounds(position) ||
                            board.stoneAt(position) == playedColor ||
                            map[position] != null

            override fun neighboursOf(position: BoardPosition) =
                    position.neighbours()
        }.fill(position)
    }

    private fun Territory.addEdgeInfo(position: BoardPosition) {
        fun isAtMinEdge(coordinate: Int) = coordinate <= 0
        fun isAtMaxEdge(coordinate: Int) = coordinate >= board.size - 1

        if (isAtMinEdge(position.x)) reachesLeftEdge = true
        if (isAtMaxEdge(position.x)) reachesRightEdge = true
        if (isAtMinEdge(position.y)) reachesTopEdge = true
        if (isAtMaxEdge(position.y)) reachesBottomEdge = true
    }

    private class Territory {
        var reachesLeftEdge = false
        var reachesRightEdge = false
        var reachesTopEdge = false
        var reachesBottomEdge = false

        val isSurrounded get() = !(reachesLeftEdge && reachesRightEdge)
                && !(reachesTopEdge && reachesBottomEdge)
    }
}

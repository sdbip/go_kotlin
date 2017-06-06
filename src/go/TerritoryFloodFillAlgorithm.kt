package go

class TerritoryFloodFillAlgorithm(
        private val board: Board,
        private val playedPosition: BoardPosition) {
    private val map = mutableMapOf<BoardPosition, Territory>()
    val playedColor = board.stoneAt(playedPosition)
            ?: throw IllegalArgumentException("Played position must contain a stone")

    fun findNewTerritories(): List<BoardPosition> {
        for (neighbour in playedPosition.neighbours())
            finder(Territory()).fillFrom(neighbour)
        return playedPosition.neighbours().filter { map[it]?.isSurrounded ?: false }
    }

    private fun finder(territory: Territory) = object : GoGameFillerAlgorithm(board, playedColor) {
        override fun paint(position: BoardPosition) {
            map[position] = territory
            territory.addEdgeInfo(position)
        }

        override fun isPainted(position: BoardPosition) =
                super.isPainted(position) || map[position] != null
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

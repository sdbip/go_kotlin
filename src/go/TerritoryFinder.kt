package go

class TerritoryFinder(
        private val board: Board,
        private val color: StoneColor) {
    private val map = mutableMapOf<BoardPosition, Boolean>()
    var reachesLeftEdge = false
    var reachesRightEdge = false
    var reachesTopEdge = false
    var reachesBottomEdge = false

    fun isTerritory(position: BoardPosition): Boolean {
        val startingPosition = position
        if (startingPosition in map) return false

        val filler = filler(color)
        filler.fillFrom(startingPosition)
        return !(reachesLeftEdge && reachesRightEdge)
                && !(reachesTopEdge && reachesBottomEdge)
    }

    private fun filler(color: StoneColor) = object : GoGameFillerAlgorithm(board, color) {
        override fun paint(position: BoardPosition) {
            map[position] = true
            addEdgeInfo(position)
        }

        override fun isPainted(position: BoardPosition) =
                super.isPainted(position) || map[position] != null
    }

    private fun addEdgeInfo(position: BoardPosition) {
        fun isAtMinEdge(coordinate: Int) = coordinate <= 0
        fun isAtMaxEdge(coordinate: Int) = coordinate >= board.size - 1

        if (isAtMinEdge(position.x)) reachesLeftEdge = true
        if (isAtMaxEdge(position.x)) reachesRightEdge = true
        if (isAtMinEdge(position.y)) reachesTopEdge = true
        if (isAtMaxEdge(position.y)) reachesBottomEdge = true
    }
}

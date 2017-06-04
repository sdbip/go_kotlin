package go

class GoGame(val board: Board = Board()) {
    val territorialMap = TerritorialMap(board)

    fun stoneAt(position: BoardPosition) = board.stoneAt(position)

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        board.placeStone(stone, at = position)
        removeSurroundedStonesNear(position)
    }

    private fun removeSurroundedStonesNear(playedPosition: BoardPosition) {
        val territories = territorialMap.territoriesNear(playedPosition)
        for ((position, _) in territories) {
            board.removeStoneAt(position)
        }
    }
}

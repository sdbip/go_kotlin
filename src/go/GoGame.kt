package go

class GoGame(private val board: Board = Board()) {
    private val territorialMap = TerritorialMap(this)

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

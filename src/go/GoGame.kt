package go

class GoGame {
    private val placedStones = mutableMapOf<BoardPosition, StoneColor>()
    private val territorialMap = TerritorialMap(this)

    fun stoneAt(position: BoardPosition) = placedStones[position]

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        placedStones[position] = stone
        removeSurroundedStonesNear(position)
    }

    private fun removeSurroundedStonesNear(playedPosition: BoardPosition) {
        val territories = territorialMap.territoriesNear(playedPosition)
        for ((position, _) in territories) {
            placedStones.remove(position)
        }
    }
}

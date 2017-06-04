package go

class GoGame {
    private val placedStones = mutableMapOf<BoardPosition, StoneColor>()

    fun stoneAt(position: BoardPosition): StoneColor? = placedStones[position]

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        placedStones[position] = stone
        removeSurroundedStonesNear(position)
    }

    private fun removeSurroundedStonesNear(playedPosition: BoardPosition) {
        val territories = TerritoryFinder(this).territoriesNear(playedPosition)
        for ((position) in territories) {
            placedStones.remove(position)
        }
    }
}

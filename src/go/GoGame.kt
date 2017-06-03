package go

class GoGame {
    private val placedStones = mutableMapOf<BoardPosition, StoneColor>()

    fun stoneAt(position: BoardPosition): StoneColor? = placedStones[position]

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        placedStones[position] = stone
        removeSurroundedStonesNear(position)
    }

    private fun removeSurroundedStonesNear(position: BoardPosition) {
        val neighbours = Delta.unitDirections.map { position + it }
        for (surrounded in neighbours.filter { isSurrounded(it) }) {
            placedStones.remove(surrounded)
        }
    }

    private fun isSurrounded(position: BoardPosition): Boolean {
        val neighbours = Delta.unitDirections.map { position + it }
        return neighbours.all { it.x < 0 || stoneAt(it) == StoneColor.black }
    }
}

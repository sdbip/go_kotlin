package go

class Board {
    private val placedStones = mutableMapOf<BoardPosition, StoneColor>()

    fun stoneAt(position: BoardPosition) = placedStones[position]

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        placedStones[position] = stone
    }

    fun removeStoneAt(position: BoardPosition) {
        placedStones.remove(position)
    }
}

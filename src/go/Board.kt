package go

class Board {
    private val placedStones = mutableMapOf<BoardPosition, StoneColor>()
    private val coordinateRange get() = 0..(size - 1)
    var size = 19

    fun stoneAt(position: BoardPosition) = placedStones[position]

    fun placeStone(stone: StoneColor, at: BoardPosition) {
        val position = at
        placedStones[position] = stone
    }

    fun removeStoneAt(position: BoardPosition) {
        placedStones.remove(position)
    }

    fun isInBounds(position: BoardPosition) =
           position.x in coordinateRange && position.y in coordinateRange
}

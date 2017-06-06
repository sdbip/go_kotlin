package go

abstract class GoGameFillerAlgorithm(
        private val board: Board,
        private val color: StoneColor)
    : FloodFillAlgorithm<BoardPosition>() {

    override fun isPainted(position: BoardPosition): Boolean =
            !board.isInBounds(position) ||
                    board.stoneAt(position) == color

    override fun neighboursOf(position: BoardPosition) = position.neighbours()
}

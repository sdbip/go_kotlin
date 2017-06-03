package go

// -Delta = Delta
operator fun Delta.unaryMinus() = Delta(right = -right, down = -down)

// BoardPosition + Delta = BoardPosition
operator fun BoardPosition.plus(delta: Delta): BoardPosition =
        BoardPosition(x + delta.right, y + delta.down)

// TODO: Add more operators
// - operator fun BoardPosition.minus(delta: Delta): BoardPosition =
//        BoardPosition(x - delta.right, y - delta.down)
// - operator fun Delta.times(factor: Int) = Delta(right = factor * right, down = factor * down)

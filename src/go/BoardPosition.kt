package go

class BoardPosition(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is BoardPosition) return super.equals(other)
        return other.x == x && other.y == y
    }

    override fun hashCode(): Int {
        return Short.MAX_VALUE * x + y
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun neighbours() = Delta.unitDirections.map { this + it }
}

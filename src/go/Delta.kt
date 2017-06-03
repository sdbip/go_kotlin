package go

class Delta(val right: Int, val down: Int) {
    companion object {
        val unitDown = Delta(right = 0, down = 1)
        val unitRight = Delta(right = 1, down = 0)
        val unitUp = -unitDown
        val unitLeft = -unitRight

        val unitDirections = arrayOf(unitUp, unitDown, unitLeft, unitRight)
    }
}

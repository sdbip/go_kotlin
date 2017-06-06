package go

abstract class FloodFillAlgorithm<T> {
    abstract fun paint(position: T)
    abstract fun isPainted(position: T): Boolean
    abstract fun neighboursOf(position: T): Iterable<T>

    fun fillFrom(startingPosition: T) {
        if (isPainted(startingPosition)) return

        paint(startingPosition)

        for (neighbour in neighboursOf(startingPosition))
            fillFrom(neighbour)
    }
}

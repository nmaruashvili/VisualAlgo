package nodar.visual.algo

import android.view.View
import androidx.core.content.res.ResourcesCompat

fun View.resize(height: Int, width: Int) {
    val layoutParams = this.layoutParams
    layoutParams.height = height
    layoutParams.width = width
    this.layoutParams = layoutParams
}

fun View.makeStartPosition() {
    this.background = ResourcesCompat.getDrawable(resources, R.drawable.start_cell_bg, null)
}

fun View.makeDestionationPosition() {
    this.background = ResourcesCompat.getDrawable(resources, R.drawable.destination_cell_bg, null)
}

//first->i second->j
fun Int.coordinate(): Pair<Int, Int> {
    return Pair(this / 14, this % 14)
}

fun Pair<Int, Int>.toIndex(): Int {
    return this.first * 14 + this.second % 14
}

fun Int.getNeighbourCells(): Set<Int> {
    val set = mutableSetOf<Int>()
    val coordinate = this.coordinate()
    if (coordinate.first - 1 >= 0) {
        set.add(Pair(coordinate.first - 1, coordinate.second).toIndex())
    }
    if (coordinate.first + 1 <= 13) {
        set.add(Pair(coordinate.first + 1, coordinate.second).toIndex())
    }
    if (coordinate.second - 1 >= 0) {
        set.add(Pair(coordinate.first, coordinate.second - 1).toIndex())
    }
    if (coordinate.second + 1 <= 13) {
        set.add(Pair(coordinate.first, coordinate.second + 1).toIndex())
    }
    return set
}
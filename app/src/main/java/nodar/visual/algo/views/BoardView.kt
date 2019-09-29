package nodar.visual.algo.views

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import nodar.visual.algo.views.BoardView.CursorType.START
import nodar.visual.algo.views.BoardView.CursorType.WALL
import nodar.visual.algo.views.BoardView.CursorType.DESTINATION
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.otaliastudios.zoom.ZoomLayout
import nodar.visual.algo.*
import nodar.visual.algo.algorithms.Djikstra

class BoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener,
    Djikstra.OnCompleteListener {

    override fun onNextStep(position: Int) {
        if (position != startPosition && position != destination) {
            cellHashMap[position]?.markAsPath()
        }
    }

    enum class CursorType {
        START, WALL, DESTINATION
    }

    private val zoomLayout: ZoomLayout
    private val cellContainer: LinearLayout
    private var boardContainerSize = 0
    private var cellHashMap = HashMap<Int, View>()
    private var djikstra: Djikstra
    private var emptyCells = ArrayList<Int>()
    private var startPosition = -1
    private var destination = -1
    private var wallPositions = ArrayList<Int>()
    private var path = ArrayList<Int>()

    var cursorType: CursorType? = null

    init {
        View.inflate(context, R.layout.board, this).apply {
            zoomLayout = findViewById(R.id.zoom_layout)
            cellContainer = findViewById(R.id.cell_container)
        }

        for (i in 0..195) {
            emptyCells.add(i)
        }

        computeCrosswordContainerSize()

        zoomLayout.resize(
            height = boardContainerSize,
            width = boardContainerSize
        )
        cellContainer.resize(
            height = boardContainerSize,
            width = boardContainerSize
        )
        djikstra = Djikstra()
        djikstra.addonCompleteListener(this)
    }

    fun startDjikstra() {
        if (isValidInput()) {
            djikstra.initialise(startPosition, destination, emptyCells)
            djikstra.djikstra()
        }
    }

    private fun isValidInput(): Boolean {
        if (destination != -1 && startPosition != -1) {
            return true
        }
        return false
    }

    fun drawBoard() {
        for (i in 0..13) {
            val hll = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
            }
            cellContainer.addView(hll)
            for (j in 0..13) {
                val cell = createCell(hll, 14 * i + j)
                hll.addView(cell)
            }
        }
    }

    private fun createCell(
        parentLayout: LinearLayout,
        cellIndex: Int
    ): View {
        val cell = LayoutInflater.from(context).inflate(R.layout.cell, parentLayout, false)
        cell.id = cellIndex
        cell.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.white_cell_bg_with_black_border,
            null
        )
        cell.setOnClickListener(this)
        cell.resize(
            height = boardContainerSize / 14,
            width = boardContainerSize / 14
        )
        cellHashMap[cellIndex] = cell
        return cell
    }

    override fun onClick(view: View) {
        when (cursorType) {
            START -> handleCursorStart(view)
            WALL -> handleCursorWall(view)
            DESTINATION -> handleCursorDestination(view)
        }
    }

    private fun handleCursorStart(view: View) {
        if (wallPositions.contains(view.id)) {
            emptyCells.add(view.id)
            wallPositions.remove(view.id)
        }
        cellHashMap[startPosition]?.reset()
        cellHashMap[view.id]?.markAsStartPosition()
        startPosition = view.id
    }

    private fun handleCursorWall(view: View) {
        if (wallPositions.contains(view.id)) {
            emptyCells.add(view.id)
            wallPositions.remove(view.id)
            cellHashMap[view.id]?.reset()
            return
        }
        if (view.id == startPosition)
            startPosition = -1
        if (view.id == destination)
            destination = -1
        cellHashMap[view.id]?.markAsWall()
        emptyCells.remove(view.id)
        wallPositions.add(view.id)
    }

    private fun handleCursorDestination(view: View) {
        if (wallPositions.contains(view.id)) {
            wallPositions.remove(view.id)
            emptyCells.add(view.id)
        }
        cellHashMap[destination]?.reset()
        cellHashMap[view.id]?.markAsDestination()
        destination = view.id
    }

    private fun computeCrosswordContainerSize() {
        val displayMetrics = DisplayMetrics()
        (context as DjikstraActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        boardContainerSize = displayMetrics.widthPixels
    }
}
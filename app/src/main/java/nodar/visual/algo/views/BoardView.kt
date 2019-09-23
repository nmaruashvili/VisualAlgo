package nodar.visual.algo.views

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
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
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val zoomLayout: ZoomLayout
    private val cellContainer: LinearLayout
    private var boardContainerSize = 0
    private var cellHashMap = HashMap<Int, View>()
    private var djikstra: Djikstra

    init {
        View.inflate(context, R.layout.board, this).apply {
            zoomLayout = findViewById(R.id.zoom_layout)
            cellContainer = findViewById(R.id.cell_container)
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
        djikstra.initialise(0,195)
        djikstra.djikstra()
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
        cellHashMap[view.id]?.makeStartPosition()
    }

    private fun computeCrosswordContainerSize() {
        val displayMetrics = DisplayMetrics()
        (context as DjikstraActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        boardContainerSize = displayMetrics.widthPixels
    }
}
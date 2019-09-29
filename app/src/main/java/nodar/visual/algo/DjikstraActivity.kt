package nodar.visual.algo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_djikstra.*
import nodar.visual.algo.views.BoardView.CursorType.START
import nodar.visual.algo.views.BoardView.CursorType.DESTINATION
import nodar.visual.algo.views.BoardView.CursorType.WALL
import nodar.visual.algo.views.NodarButton

class DjikstraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_djikstra)
        board.drawBoard()
        start_djikstra.setOnClickListener {
            board.startDjikstra()
        }
        start_location.nodarButtonClickListener =
            object : NodarButton.NodarButtonClickListener {
                override fun onNodarButtonClicked() {
                    handleStartLocationButtonClick()
                    board.cursorType = START
                }
            }
        destination.nodarButtonClickListener =
            object : NodarButton.NodarButtonClickListener {
                override fun onNodarButtonClicked() {
                    handleDestinationButtonClick()
                    board.cursorType = DESTINATION
                }
            }
        wall.nodarButtonClickListener =
            object : NodarButton.NodarButtonClickListener {
                override fun onNodarButtonClicked() {
                    handleWallButtonClick()
                    board.cursorType = WALL
                }
            }
    }

    private fun handleStartLocationButtonClick() {
        wall.resetButtonState()
        destination.resetButtonState()
    }

    private fun handleDestinationButtonClick() {
        start_location.resetButtonState()
        wall.resetButtonState()
    }

    private fun handleWallButtonClick() {
        start_location.resetButtonState()
        destination.resetButtonState()
    }
}

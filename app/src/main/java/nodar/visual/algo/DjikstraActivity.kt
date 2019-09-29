package nodar.visual.algo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_djikstra.*
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
                    Log.d("clicked?","true")
                }
            }
        destination.nodarButtonClickListener =
            object : NodarButton.NodarButtonClickListener {
                override fun onNodarButtonClicked() {
                    Log.d("clicked?","true")
                }
            }
        wall.nodarButtonClickListener =
            object : NodarButton.NodarButtonClickListener {
                override fun onNodarButtonClicked() {
                    Log.d("clicked?","true")
                }
            }
    }
}

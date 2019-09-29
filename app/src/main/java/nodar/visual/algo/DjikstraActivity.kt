package nodar.visual.algo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_djikstra.*

class DjikstraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_djikstra)
        board.drawBoard()
        start_djikstra.setOnClickListener {
            board.startDjikstra()
        }
    }
}

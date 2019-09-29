package nodar.visual.algo.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import nodar.visual.algo.R

class NodarButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr), View.OnClickListener {

    private var _isSelected = false

    init {
        setOnClickListener(this)
    }

    interface NodarButtonClickListener {
        fun onNodarButtonClicked()
    }

    var nodarButtonClickListener: NodarButtonClickListener? = null

    override fun onClick(view: View) {
        toggleButtonState()
        nodarButtonClickListener?.onNodarButtonClicked()
    }

    fun resetButtonState() {
        _isSelected = true
        toggleButtonState()
    }

    private fun toggleButtonState() {
        _isSelected = !_isSelected
        isSelected = _isSelected
        toggleTextColor()
    }

    private fun toggleTextColor() {
        if (_isSelected)
            setTextColor(resources.getColor(R.color.white))
        else
            setTextColor(resources.getColor(R.color.black))
    }

}
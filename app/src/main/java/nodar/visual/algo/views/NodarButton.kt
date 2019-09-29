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
        _isSelected = !_isSelected
        view.isSelected = _isSelected
        toggleTextColor()
        nodarButtonClickListener?.onNodarButtonClicked()
    }

    fun toggleTextColor() {
        if (_isSelected)
            setTextColor(resources.getColor(R.color.white))
        else
            setTextColor(resources.getColor(R.color.black))
    }

}
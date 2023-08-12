package zupzup.manager.ui.orderdetail.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OrderDetailItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if(parent.getChildAdapterPosition(view) == 1) {
            outRect.top = 16
        }
        else if(parent.getChildAdapterPosition(view) == 2) {
            outRect.top = 32
        }
        else if(parent.getChildAdapterPosition(view) == 3) {
            outRect.top = 5
        }
        else {
            outRect.top = 8
        }
    }
}
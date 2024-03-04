package zupzup.manager.ui.orderlist.recyclerview

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OrderListItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 24
        if(parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.bottom = 24
        }
    }
}
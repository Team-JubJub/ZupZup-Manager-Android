package zupzup.manager.ui.orderlist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zupzup.manager.databinding.ItemOrderCardBinding
import zupzup.manager.domain.models.order.OrderModel

class OrderListRcvAdapter(
    private val navigateToOrderDetail: (order: OrderModel) -> Unit
) :
    ListAdapter<OrderModel, OrderListRcvAdapter.OrderListViewHolder>(
        OrderModelDiffCallBack()
    ) {

    class OrderListViewHolder(
        private val binding: ItemOrderCardBinding,
        private val navigateToOrderDetail: (order: OrderModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderModel) {
            with(binding) {
                order = item
                navigate = navigateToOrderDetail
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding =
            ItemOrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding, navigateToOrderDetail)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderModelDiffCallBack : DiffUtil.ItemCallback<OrderModel>() {
        override fun areItemsTheSame(
            oldItem: OrderModel,
            newItem: OrderModel
        ): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(
            oldItem: OrderModel,
            newItem: OrderModel
        ): Boolean {
            return oldItem.orderId == newItem.orderId && oldItem.orderStatus == newItem.orderStatus
        }
    }

}
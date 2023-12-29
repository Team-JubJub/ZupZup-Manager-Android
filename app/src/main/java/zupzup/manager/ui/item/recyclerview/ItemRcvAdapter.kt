package zupzup.manager.ui.item.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zupzup.manager.databinding.ItemItemInfoBinding
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.ui.item.ItemViewModel
import zupzup.manager.ui.item.clicklistener.ItemBtnClickListener

class ItemRcvAdapter(
    private val itemBtnClickListener: ItemBtnClickListener,
    private val itemViewModel: ItemViewModel,
    private val fragmentLifecycleOwner: LifecycleOwner
) : ListAdapter<ItemModel, ItemRcvAdapter.ItemViewHolder>(
    ItemModelDiffCallBack()
) {
    class ItemViewHolder(
        private val binding: ItemItemInfoBinding,
        private val itemBtnClickListener: ItemBtnClickListener,
        private val itemViewModel: ItemViewModel,
        private val fragmentLifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemModel) {
            with(binding) {
                this.item = item
                clickListener = itemBtnClickListener
                lifecycleOwner = fragmentLifecycleOwner
                viewModel = itemViewModel
                executePendingBindings()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        val binding =
            ItemItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, itemBtnClickListener, itemViewModel, fragmentLifecycleOwner)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        }

    // 비교할 부분 추가완료 -> imageURL은 서버에서 받아와야 이전 값과 비교 가능함 (TODO : ItemViewModel에 구현)
    class ItemModelDiffCallBack : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {
            return (oldItem.itemId == newItem.itemId) && (oldItem.itemCount == newItem.itemCount) &&
                (oldItem.itemName == newItem.itemName) && (oldItem.imageURL == newItem.imageURL) &&
                (oldItem.itemPrice == newItem.itemPrice) && (oldItem.salePrice == newItem.salePrice)
        }

        override fun areContentsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }
    }
}
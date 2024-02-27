package zupzup.manager.ui.orderdetail

import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.ui.orderdetail.models.OrderDetailHeaderModel
import zupzup.manager.ui.orderdetail.models.OrderDetailViewType

interface OrderDetailFragmentClickListener {

    fun confirmOrder(orderModel: OrderModel, isPartial: Boolean)

    fun rejectOrder(orderModel: OrderModel)

    fun cancelOrder(orderDetailHeader: OrderDetailHeaderModel, orderDetailBody: List<OrderDetailViewType>)

    fun completeOrder(orderId: Long)

    fun onBackBtnClick()
}
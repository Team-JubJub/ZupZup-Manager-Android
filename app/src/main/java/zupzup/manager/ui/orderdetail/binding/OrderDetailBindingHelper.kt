package zupzup.manager.ui.orderdetail.binding

import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.models.order.OrderSpecificModel
import zupzup.manager.ui.common.ViewType
import zupzup.manager.ui.orderdetail.models.OrderDetailHeaderModel
import zupzup.manager.ui.orderdetail.models.OrderDetailViewType
import javax.inject.Inject

class OrderDetailBindingHelper @Inject constructor(
    private val onCreateOrderConfirmBottomSheetButtonClick: (order: OrderModel, isPartial: Boolean) -> Unit,
    private val plusOrderItemConfirmedAmount: (itemId: Long) -> Unit,
    private val minusOrderItemConfirmedAmount: (itemId: Long) -> Unit,
) {
    fun createOrderConfirmBottomSheet(
        orderDetailHeader: OrderDetailHeaderModel,
        orderDetailBody: List<OrderDetailViewType>
    ) {
        val customerInfo =
            orderDetailBody.find { it.viewType == ViewType.CUSTOMER_INFO.ordinal } as OrderDetailViewType.OrderCustomerInfoViewType
        val orderList = orderDetailBody.filter { it.viewType == ViewType.ORDER_ITEM.ordinal }
            .map { it as OrderDetailViewType.OrderItemViewType }

        val confirmedOrderModel = OrderModel(
            orderId = orderDetailHeader.orderId,
            storeId = orderDetailHeader.storeId,
            customer = customerInfo.customer,
            orderStatus = orderDetailHeader.orderStatus,
            orderTitle = orderDetailHeader.orderTitle,
            orderTime = orderDetailHeader.orderTime,
            visitTime = customerInfo.visitTime,
            storeName = orderDetailHeader.storeName,
            storeAddress = orderDetailHeader.storeAddress,
            category = orderDetailHeader.category,
            orderList = orderList.map {
                OrderSpecificModel(
                    itemId = it.orderItem.itemId,
                    itemName = it.orderItem.itemName,
                    imageUrl = it.orderItem.imageUrl,
                    itemPrice = it.orderItem.itemPrice,
                    salePrice = it.orderItem.salePrice,
                    itemCount = it.getConfirmedAmount()
                )
            }
        )
        onCreateOrderConfirmBottomSheetButtonClick(
            confirmedOrderModel,
            orderList.find { it.orderItem.itemCount != it.getConfirmedAmount() } != null
        )
    }

    fun onPlusOrderItemConfirmedAmountBtnClick(itemId: Long) {
        plusOrderItemConfirmedAmount(itemId)
    }

    fun onMinusOrderItemConfirmedAmountBtnClick(itemId: Long) {
        minusOrderItemConfirmedAmount(itemId)
    }
}
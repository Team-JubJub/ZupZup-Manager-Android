package zupzup.manager.data.dto.order.parameter

data class OrderSpecificListRequest(
    val orderList: List<OrderSpecificDto> = listOf()
)
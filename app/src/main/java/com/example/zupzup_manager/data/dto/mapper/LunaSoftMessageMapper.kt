package com.example.zupzup_manager.data.dto.mapper

import com.example.zupzup_manager.data.common.Constants
import com.example.zupzup_manager.data.dto.lunasoft.parameter.Message
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.models.OrderSpecificModel

object LunaSoftMessageMapper {

    fun getNotificationTalkTemplateId(state: String): Int {
        return when (state) {
            Constants.stateConfirm -> Constants.confirmTemplateId
            Constants.stateCancel -> Constants.rejectTemplateId
            Constants.statePartialConfirm -> Constants.partialConfirmTemplateId
            else -> 0
        }
    }

    fun getNotificationTalkMessage(
        orderModel: OrderModel,
        state: String
    ): List<Message> {
        val list = mutableListOf<Message>()
        val messageContent = when (state) {
            Constants.stateConfirm -> getConfirmMessageContent(orderModel)
            Constants.statePartialConfirm -> getPartialConfirmMessageContent(orderModel)
            Constants.stateCancel -> getCancelMessageContent(orderModel)
            else -> ""
        }
        list.add(
            Message(
                no = "0",
                telNum = orderModel.customer.phoneNumber,
                msgContent = messageContent,
                smsContent = "",
                useSms = "0"
            )
        )
        return list.toList()
    }

    private fun getConfirmMessageContent(orderModel: OrderModel): String {
        return "<예약 확정 안내>\n" +
                "${orderModel.customer.name}님 예약이 확정되었어요.\n" +
                "시간에 맞게 매장으로 방문 부탁드려요.\n\n" +
                "-예약 매장: ${Constants.storeName}\n" +
                "-예약 제품: ${getCartListText(orderModel.orderList)}\n" +
                "-예정 방문 시간: ${orderModel.visitTime}\n\n\n" +
                "*예약취소, 방문 시간 변경 등 문의사항이 있으시면 줍줍 카카오톡 채널을 통해 상담직원에게 메시지를 보내주세요.\n\n" +
                "줍줍을 이용해 주셔서 감사해요 :)"
    }

    private fun getPartialConfirmMessageContent(orderModel: OrderModel): String {
        return "<예약 부분 확정 안내>\n" +
                "${orderModel.customer.name}님 현재 남은 제품들을 반영하여 주문이 수정되었어요. 주문내역을 다시 한번 확인해 주세요.\n\n" +
                "-예약 매장: ${Constants.storeName}\n" +
                "-예약 제품: ${getCartListText(orderModel.orderList)}\n" +
                "-예정 방문 시간: ${orderModel.visitTime}\n\n\n" +
                "*예약취소, 방문 시간 변경 등 문의사항이 있으시면 줍줍 카카오톡 채널을 통해 상담직원에게 메시지를 보내주세요.\n\n" +
                "줍줍을 이용해 주셔서 감사해요 :)"
    }

    private fun getCancelMessageContent(orderModel: OrderModel): String {
        return "<예약 취소 안내>\n" +
                "안녕하세요.${orderModel.customer.name}님!\n" +
                "취소된 예약에 대해 안내드려요.\n\n" +
                "-예약 매장: ${Constants.storeName}\n" +
                "-예약 제품: ${getCartListText(orderModel.orderList)}\n" +
                "-예정 방문 시간: ${orderModel.visitTime}\n" +
                "-취소 사유: 재고소진으로 인한 취소\n\n" +
                "*문의사항이 있으시면 줍줍 카카오톡 채널을 통해 상담직원에게 메시지를 보내주세요."
    }

    private fun getCartListText(cartList: List<OrderSpecificModel>): String {
        var text = ""
        cartList.forEachIndexed { idx, item ->
            text += "${item.itemName} ${item.itemCount}개"
            if (idx != cartList.lastIndex) {
                text += ", "
            }
        }
        return text
    }
}
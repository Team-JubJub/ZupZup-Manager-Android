package zupzup.manager.ui.common

sealed class UiEventState {
    object Processing : UiEventState()
    object Complete : UiEventState()
    data class Fail(val errorMessage: String) : UiEventState()
}

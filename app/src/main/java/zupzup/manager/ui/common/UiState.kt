package zupzup.manager.ui.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

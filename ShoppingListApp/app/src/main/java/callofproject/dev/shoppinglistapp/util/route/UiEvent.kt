package callofproject.dev.shoppinglistapp.util.route

sealed class UiEvent {
    data object Success: UiEvent()
    data object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
    data class ShowToastMessage(val message: UiText): UiEvent()
}
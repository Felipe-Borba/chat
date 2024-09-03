sealed interface MainAction {
    data object OnFinishLogin : MainAction
    data object SendMessage : MainAction
    data class ChangeUserName(val userName: String) : MainAction
    data class ChangeMessage(val message: String) : MainAction
}

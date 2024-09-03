sealed interface MainAction {
    data class OnFinishLogin(val userName: String) : MainAction
    data class SendMessage(val message: String) : MainAction
}

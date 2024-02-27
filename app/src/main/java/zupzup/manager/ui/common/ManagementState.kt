package zupzup.manager.ui.common

sealed class ManagementState{
    object DefaultMode : ManagementState()
    object AmountMode : ManagementState()
    object InfoMode : ManagementState()
}

package doro.android.domain.enum


enum class CherryUI {
    home, profile, notification, setting, game, login, sign_up, reset_password, point_change, splash
}

enum class CherryAction {
    enter, clicked, ping
}


enum class CherryCameraMode {
    invisible, half, small
}

enum class CherryButtonEvent {
    log_in_button, log_out_button, sign_up_button, send_verification_email_button, available_machine_button,
}

interface CherryActionData

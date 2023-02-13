package doro.android.domain.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


enum class CherryUI {
    home, profile, notification, setting, game, login, sign_up, reset_password, point_exchange, splash
}

enum class CherryAction {
    visited, clicked, ping
}


enum class CherryCameraMode {
    invisible, half, small
}

enum class CherryButtonEvent {
    log_in_button, log_out_button, sign_up_button, send_verification_email_button, notification, profile, setting,
}

enum class CherryGameButtonName{
    credit_in, credit_out, help, line, bet, start, auto_start, exit, change_camera, available_machine_button
}

@Parcelize
open class CherryActionData : Parcelable {}

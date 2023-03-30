package doro.android.core.util

object UserStatus {
    private var _currentPlace: String = ""
    val currentPlace: String get() = _currentPlace

    fun isInGame() = _currentPlace == "game"

    fun setCurrentPlace(place: String) {
        _currentPlace = place
    }

}
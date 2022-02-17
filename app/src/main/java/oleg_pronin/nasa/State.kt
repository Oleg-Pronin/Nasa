package oleg_pronin.nasa

sealed class State {
    data class Success(val data: Any?) : State()
    data class Error(val error: Throwable) : State()
    object Loading : State()
}
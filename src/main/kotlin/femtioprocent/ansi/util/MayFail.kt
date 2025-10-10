package femtioprocent.partytalk.util

sealed class MayFail<out Tok, out Tfail : Failure> {
    data class Ok<Tok>(val t: Tok) : MayFail<Tok, Nothing>()
    data class Failed<Tfail : Failure>(val t: Tfail) : MayFail<Nothing, Tfail>()
}

open class Failure(protected val error: String) {
    open fun why(): String {
        return "Failed error: $error"
    }
}

class FatFailure(error: String, vararg val more: Pair<String, String>) : Failure(error) {
    override fun why(): String {
        return "Failed fat error: $error; ${more.joinToString("; ") { (l, r) -> "$l: $r" }}"
    }
}
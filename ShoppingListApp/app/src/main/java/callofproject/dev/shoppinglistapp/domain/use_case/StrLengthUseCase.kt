package callofproject.dev.shoppinglistapp.domain.use_case

import callofproject.dev.shoppinglistapp.util.MAX_LENGTH

class StrLengthUseCase {
    operator fun invoke(str: String): Boolean = str.length < MAX_LENGTH
}
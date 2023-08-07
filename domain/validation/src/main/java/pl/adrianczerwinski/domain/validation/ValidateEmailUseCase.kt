package pl.adrianczerwinski.domain.validation

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String, canBeEmpty: Boolean = false): Boolean {
        if (email.isBlank()) {
            return canBeEmpty
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }
}

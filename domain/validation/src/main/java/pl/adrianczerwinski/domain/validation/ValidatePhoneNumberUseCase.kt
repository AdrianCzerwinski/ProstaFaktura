package pl.adrianczerwinski.domain.validation

import android.util.Patterns
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String): Boolean {
        if (phoneNumber.isBlank()) {
            return false
        }
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return false
        }
        return true
    }
}

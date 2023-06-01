package pl.adrianczerwinski.domain.validation

import javax.inject.Inject

class ValidatePostalCodeUseCase @Inject constructor() {
    operator fun invoke(postalCode: String): Boolean {
        if (postalCode.isBlank()) {
            return false
        }
        if (!postalCode.replace("-", "").matches("^[0-9]+$".toRegex())) {
            return false
        }
        return true
    }
}

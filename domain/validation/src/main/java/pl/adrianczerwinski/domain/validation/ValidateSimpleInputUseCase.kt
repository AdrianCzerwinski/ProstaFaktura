package pl.adrianczerwinski.domain.validation

import javax.inject.Inject

class ValidateSimpleInputUseCase @Inject constructor() {
    operator fun invoke(value: String): Boolean {
        if (value.isBlank()) {
            return false
        }
        return true
    }
}

package com.example.domain.util

/**
 * Validates email addresses using manual parsing rules.
 *
 * This validator performs structural checks without relying on regular expressions,
 * ensuring readability and Kotlin Multiplatform compatibility.
 *
 * Validation rules:
 * - Must not be null or blank.
 * - Must contain exactly one `@` symbol, not at the start.
 * - Local part (before `@`) must contain only letters, digits, or `._%+-`.
 * - Domain part (after `@`) must not be empty, must contain a dot,
 *   must not start or end with a dot, and must only contain letters, digits, `.` or `-`.
 * - Consecutive dots (`..`) are not allowed anywhere in the address.
 */
object EmailValidator {

    /**
     * Validates whether the given [email] string is a structurally valid email address.
     *
     * @param email The email address to validate. May be null.
     * @return `true` if the email passes all structural checks, `false` otherwise.
     */
    fun validate(email: String?): Boolean {
        if (email.isNullOrBlank()) return false

        val trimmed: String = email.trim()
        val atSignIndex: Int = trimmed.indexOf('@')

        // No '@' found — not a valid email
        if (atSignIndex == -1) return false
        // '@' at the very start means the local part is empty
        if (atSignIndex == 0) return false
        // More than one '@' is not allowed
        if (atSignIndex != trimmed.lastIndexOf('@')) return false

        val localPart: String = trimmed.substring(0, atSignIndex)

        // Local part must not be empty (e.g. "@example.com" is invalid)
        if (localPart.isEmpty()) return false

        // Local part must contain only allowed characters: letters, digits, '.', '_', '%', '+', '-'
        if (!localPart.all { symbol: Char ->
                symbol.isLetterOrDigit() || symbol in "._%+-"
            }) {
            return false
        }

        val domainPart: String = trimmed.substring(atSignIndex + 1)

        // Domain must not be empty (e.g. "user@" is invalid)
        if (domainPart.isEmpty()) return false

        // Domain must contain at least one dot (e.g. "user@localhost" is rejected)
        if (!domainPart.contains('.')) return false

        // Domain must not start or end with a dot
        if (domainPart.startsWith('.') || domainPart.endsWith('.')) return false

        // Consecutive dots are not allowed anywhere in the address
        if (trimmed.contains("..")) return false

        // Domain must contain only allowed characters: letters, digits, '.', '-'
        if (!domainPart.all { symbol: Char ->
                symbol.isLetterOrDigit() || symbol in ".-"
            }) {
            return false
        }

        return true
    }
}
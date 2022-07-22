package network.oxalis.commons.certvalidator.api;

/**
 * Generic exception for project.
 */
public class CertificateValidationException extends Exception {
    public CertificateValidationException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public CertificateValidationException(String message) {
        super(message);
    }
}

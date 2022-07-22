package network.oxalis.commons.certvalidator.lang;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;

public class ValidatorParsingException extends CertificateValidationException {

    public ValidatorParsingException(String message) {
        super(message);
    }

    public ValidatorParsingException(String reason, Throwable cause) {
        super(reason, cause);
    }
}

package network.oxalis.commons.certvalidator.api;

/**
 * @author erlend
 */
public interface ErrorHandler {

    void handle(CertificateValidationException e) throws FailedValidationException;

}

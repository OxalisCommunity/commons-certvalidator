package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.FailedValidationException;

import java.security.cert.X509Certificate;

/**
 * Validation making sure certificate doesn't expire in n milliseconds.
 */
public class ExpirationSoonRule extends AbstractRule {

    private long millis;

    public ExpirationSoonRule(long millis) {
        this.millis = millis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(X509Certificate certificate) throws CertificateValidationException {
        if (certificate.getNotAfter().getTime() < (System.currentTimeMillis() + millis))
            throw new FailedValidationException(String.format("Certificate expires in less than %s milliseconds.", millis));
    }
}

package network.oxalis.commons.certvalidator.api;

import java.security.cert.X509CRL;

public interface CrlFetcher {
    X509CRL get(String url) throws CertificateValidationException;
}

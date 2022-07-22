package network.oxalis.commons.certvalidator.api;

import java.security.cert.X509CRL;

public interface CrlCache extends CrlFetcher {
    void set(String url, X509CRL crl);
}

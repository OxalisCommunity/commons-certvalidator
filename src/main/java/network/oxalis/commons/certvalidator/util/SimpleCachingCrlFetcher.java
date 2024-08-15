package network.oxalis.commons.certvalidator.util;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.CrlCache;
import network.oxalis.commons.certvalidator.api.CrlFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;

/**
 * Simple implementation of CRL fetcher, which caches downloaded CRLs. If a CRL is not cached, or the Next update-
 * field of a cached CRL indicates there is an updated CRL available, an updated CRL will immediately be downloaded.
 */
public class SimpleCachingCrlFetcher implements CrlFetcher {

    protected CrlCache crlCache;

    public SimpleCachingCrlFetcher(CrlCache crlCache) {
        this.crlCache = crlCache;
    }

    @Override
    public X509CRL get(String url) throws CertificateValidationException {
        X509CRL crl = crlCache.get(url);
        if (crl == null) {
            // Not in cache
            crl = download(url);
        } else if (crl.getNextUpdate() != null && crl.getNextUpdate().getTime() < System.currentTimeMillis()) {
            // Outdated
            crl = download(url);
        } else if (crl.getNextUpdate() == null) {
            // No action.
        }
        return crl;
    }

    protected X509CRL download(String url) throws CertificateValidationException {
        if (url != null && url.matches("http[s]{0,1}://.*")) {
            X509CRL crl = httpDownload(url);
            crlCache.set(url, crl);
            return crl;
        } else if (url != null && url.startsWith("ldap://")) {
            // Currently not supported.
            return null;
        }

        return null;
    }

    protected X509CRL httpDownload(String url) throws CertificateValidationException {
        try {
            URLConnection urlConnection = URI.create(url).toURL().openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            InputStream inputStream = urlConnection.getInputStream();
            return CrlUtils.load(inputStream);
        } catch (IOException | CRLException e) {
            throw new CertificateValidationException(String.format("Failed to download CRL '%s' (%s)", url, e.getMessage()), e);
        }
    }
}

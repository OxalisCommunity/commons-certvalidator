package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.CrlCache;
import network.oxalis.commons.certvalidator.api.CrlFetcher;
import network.oxalis.commons.certvalidator.api.FailedValidationException;
import network.oxalis.commons.certvalidator.util.SimpleCachingCrlFetcher;
import network.oxalis.commons.certvalidator.util.SimpleCrlCache;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;

import java.io.IOException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CRLRule extends AbstractRule {

    private static final String CRL_EXTENSION = "2.5.29.31";

    private CrlFetcher crlFetcher;

    public CRLRule(CrlFetcher crlFetcher) {
        this.crlFetcher = crlFetcher;
    }

    public CRLRule(CrlCache crlCache) {
        this(new SimpleCachingCrlFetcher(crlCache));
    }

    public CRLRule() {
        this.crlFetcher = new SimpleCachingCrlFetcher(new SimpleCrlCache());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(X509Certificate certificate) throws CertificateValidationException {
        List<String> urls = getCrlDistributionPoints(certificate);
        for (String url : urls) {
            X509CRL crl = crlFetcher.get(url);
            if (crl != null)
                if (crl.isRevoked(certificate))
                    throw new FailedValidationException("Certificate is revoked.");
        }
    }

    public static List<String> getCrlDistributionPoints(X509Certificate certificate) throws CertificateValidationException {
        try {
            ArrayList<String> urls = new ArrayList<>();

            if (!certificate.getNonCriticalExtensionOIDs().contains(CRL_EXTENSION))
                return urls;

            CRLDistPoint distPoint = CRLDistPoint.getInstance(JcaX509ExtensionUtils.parseExtensionValue(certificate.getExtensionValue(CRL_EXTENSION)));
            for (DistributionPoint dp : distPoint.getDistributionPoints())
                for (GeneralName name : ((GeneralNames) dp.getDistributionPoint().getName()).getNames())
                    if (name.getTagNo() == GeneralName.uniformResourceIdentifier)
                        urls.add(((DERIA5String) name.getName()).getString());

            return urls;
        } catch (IOException | NullPointerException e) {
            throw new CertificateValidationException(e.getMessage(), e);
        }
    }
}

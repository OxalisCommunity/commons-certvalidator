package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.api.*;
import network.oxalis.pkix.ocsp.CertificateResult;
import network.oxalis.pkix.ocsp.OcspClient;
import network.oxalis.pkix.ocsp.OcspException;
import network.oxalis.pkix.ocsp.OcspServerException;
import network.oxalis.commons.certvalidator.util.SimpleProperty;

import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

/**
 * @author erlend
 */
public class OCSPRule extends AbstractRule {

    public static final Property<CertificateResult> RESULT = SimpleProperty.create();

    protected OcspClient ocspClient;

    public OCSPRule(CertificateBucket intermediateCertificates) {
        ocspClient = OcspClient.builder()
                .set(OcspClient.INTERMEDIATES, intermediateCertificates.asList())
                .build();
    }

    public OCSPRule(OcspClient ocspClient) {
        this.ocspClient = ocspClient;
    }

    @Override
    public Report validate(X509Certificate certificate, Report report) throws CertificateValidationException {
        try {
            report.set(RESULT, ocspClient.verify(certificate));

            return report;
        } catch (OcspServerException e) {
            throw new CertificateValidationException(e.getMessage(), e);
        } catch (OcspException e) {
            if (e.getCause() instanceof UnknownHostException)
                throw new CertificateValidationException(e.getMessage(), e);
            else
                throw new FailedValidationException(e.getMessage(), e);
        } catch (Exception e) {
            throw new CertificateValidationException(e.getMessage(), e);
        }
    }
}

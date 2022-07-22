package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.Report;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.util.DummyReport;

import java.security.cert.X509Certificate;

/**
 * @author erlend
 */
public abstract class AbstractRule implements ValidatorRule {

    @Override
    public Report validate(X509Certificate certificate, Report report) throws CertificateValidationException {
        validate(certificate);
        
        return report;
    }

    @Override
    public void validate(X509Certificate certificate) throws CertificateValidationException {
        validate(certificate, DummyReport.INSTANCE);
    }
}

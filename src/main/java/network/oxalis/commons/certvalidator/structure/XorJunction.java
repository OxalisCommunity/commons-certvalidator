package network.oxalis.commons.certvalidator.structure;

import network.oxalis.commons.certvalidator.api.Report;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.FailedValidationException;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Allows combining instances of validators using a limited set of logic.
 */
public class XorJunction extends AbstractJunction {

    public XorJunction(ValidatorRule... validatorRules) {
        super(validatorRules);
    }

    public XorJunction(List<ValidatorRule> validatorRules) {
        super(validatorRules);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report validate(X509Certificate certificate, Report report) throws CertificateValidationException {
        List<CertificateValidationException> exceptions = new ArrayList<>();

        for (ValidatorRule validatorRule : validatorRules) {
            try {
                report = validatorRule.validate(certificate, report.copy());
            } catch (CertificateValidationException e) {
                exceptions.add(e);
            }
        }

        if (exceptions.size() != validatorRules.size() - 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Xor-junction failed with results (%s of %s):", exceptions.size(), validatorRules.size()));
            for (Exception e : exceptions)
                stringBuilder.append("\n* ").append(e.getMessage());

            throw new FailedValidationException(stringBuilder.toString());
        }

        return report;
    }
}

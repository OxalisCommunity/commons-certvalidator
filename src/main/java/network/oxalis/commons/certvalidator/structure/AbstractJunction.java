package network.oxalis.commons.certvalidator.structure;

import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.util.DummyReport;
import network.oxalis.commons.certvalidator.api.CertificateValidationException;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractJunction implements ValidatorRule {

    protected List<ValidatorRule> validatorRules = new ArrayList<>();

    public AbstractJunction(ValidatorRule... validatorRules) {
        addRule(validatorRules);
    }

    public AbstractJunction(List<ValidatorRule> validatorRules) {
        addRule(validatorRules);
    }

    public AbstractJunction addRule(ValidatorRule... validatorRules) {
        this.validatorRules.addAll(Arrays.asList(validatorRules));
        return this;
    }

    public AbstractJunction addRule(List<ValidatorRule> validatorRules) {
        this.validatorRules.addAll(validatorRules);
        return this;
    }

    @Override
    public void validate(X509Certificate certificate) throws CertificateValidationException {
        validate(certificate, DummyReport.INSTANCE);
    }
}

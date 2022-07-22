package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.Validator;
import network.oxalis.commons.certvalidator.ValidatorBuilder;
import network.oxalis.commons.certvalidator.api.CertificateBucket;
import network.oxalis.commons.certvalidator.api.FailedValidationException;
import network.oxalis.commons.certvalidator.util.KeyStoreCertificateBucket;
import network.oxalis.commons.certvalidator.util.SimpleReport;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChainRuleTest {

    @Test(enabled = false)
    public void simple() throws Exception {
        KeyStoreCertificateBucket keyStoreCertificateBucket = new KeyStoreCertificateBucket("JKS", getClass().getResourceAsStream("/peppol-test.jks"), "peppol");
        CertificateBucket rootCertificates = keyStoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keyStoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        Validator validator = ValidatorBuilder.newInstance()
                .addRule(new ChainRule(rootCertificates, intermediateCertificates))
                .build();

        validator.validate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer"), SimpleReport.newInstance());
        validator.validate(getClass().getResourceAsStream("/peppol-test-smp-difi.cer"));

        try {
            validator.validate(getClass().getResourceAsStream("/peppol-prod-smp-difi.cer"));
            Assert.fail("Exception expected.");
        } catch (FailedValidationException e) {
            // No action.
        }
    }

}

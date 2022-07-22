package network.oxalis.commons.certvalidator.rule;


import network.oxalis.commons.certvalidator.Validator;
import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.FailedValidationException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SigningRuleTest {

    @Test
    public void publiclySignedExpectedWithPubliclySigned() throws Exception {
        SigningRule.PublicSignedOnly()
                .validate(Validator.getCertificate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer")));
    }

    @Test(expectedExceptions = FailedValidationException.class)
    public void selfSignedExpectedWithPubliclySigned() throws Exception {
        SigningRule.SelfSignedOnly()
                .validate(Validator.getCertificate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer")));
    }

    @Test(expectedExceptions = FailedValidationException.class)
    public void publiclySignedExpectedWithSelfSigned() throws Exception {
        SigningRule.PublicSignedOnly()
                .validate(Validator.getCertificate(getClass().getResourceAsStream("/selfsigned.cer")));
    }

    @Test
    public void selfSignedExpectedWithSelfSigned() throws Exception {
        SigningRule.SelfSignedOnly()
                .validate(Validator.getCertificate(getClass().getResourceAsStream("/selfsigned.cer")));
    }

    @Test(expectedExceptions = CertificateValidationException.class)
    public void triggerException() throws Exception {
        SigningRule.PublicSignedOnly().validate(null);
    }

    @Test
    public void enumStuff() {
        for (SigningRule.Kind kind : SigningRule.Kind.values())
            Assert.assertNotNull(SigningRule.Kind.valueOf(kind.name()));
    }
}

package network.oxalis.commons.certvalidator.rule;

import network.oxalis.commons.certvalidator.Validator;
import network.oxalis.commons.certvalidator.util.SimpleCertificateBucket;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.security.cert.X509Certificate;

/**
 * @author erlend
 */
public class BlacklistRuleTest {

    @Test
    public void simple() throws Exception {
        X509Certificate certificate;

        try (InputStream inputStream = getClass().getResourceAsStream("/selfsigned.cer")) {
            certificate = Validator.getCertificate(inputStream);
        }

        Assert.assertFalse(new Validator(new BlacklistRule(SimpleCertificateBucket.with(certificate))).isValid(certificate));
        Assert.assertTrue(new Validator(new BlacklistRule(SimpleCertificateBucket.with())).isValid(certificate));
    }

}

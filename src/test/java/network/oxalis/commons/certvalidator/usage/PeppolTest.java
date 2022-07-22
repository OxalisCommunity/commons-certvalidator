package network.oxalis.commons.certvalidator.usage;

import network.oxalis.commons.certvalidator.Validator;
import network.oxalis.commons.certvalidator.ValidatorBuilder;
import network.oxalis.commons.certvalidator.util.KeyStoreCertificateBucket;
import network.oxalis.commons.certvalidator.util.SimpleCrlCache;
import network.oxalis.commons.certvalidator.util.SimplePrincipalNameProvider;
import network.oxalis.commons.certvalidator.api.CertificateBucket;
import network.oxalis.commons.certvalidator.api.CrlCache;
import network.oxalis.commons.certvalidator.rule.*;
import org.testng.annotations.Test;

public class PeppolTest {

    private CrlCache crlCache = new SimpleCrlCache();

    @Test(enabled = false)
    public void simpleTestAp() throws Exception {
        KeyStoreCertificateBucket keyStoreCertificateBucket = new KeyStoreCertificateBucket(getClass().getResourceAsStream("/peppol-test.jks"), "peppol");
        CertificateBucket rootCertificates = keyStoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keyStoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        Validator valvalidatordatorHelper = ValidatorBuilder.newInstance()
                .addRule(new ExpirationRule())
                .addRule(new SigningRule())
                .addRule(new PrincipalNameRule("CN", new SimplePrincipalNameProvider("PEPPOL ACCESS POINT TEST CA"), PrincipalNameRule.Principal.ISSUER))
                .addRule(new ChainRule(rootCertificates, intermediateCertificates))
                .addRule(new CRLRule(crlCache))
                .addRule(new OCSPRule(intermediateCertificates))
                .build();

        valvalidatordatorHelper.validate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer"));
        valvalidatordatorHelper.validate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer"));
    }

    @Test(enabled = false)
    public void simpleTestSmp() throws Exception {
        KeyStoreCertificateBucket keyStoreCertificateBucket = new KeyStoreCertificateBucket(getClass().getResourceAsStream("/peppol-test.jks"), "peppol");
        CertificateBucket rootCertificates = keyStoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keyStoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        ValidatorBuilder.newInstance()
                .addRule(new ExpirationRule())
                .addRule(new SigningRule())
                .addRule(new PrincipalNameRule("CN", new SimplePrincipalNameProvider("PEPPOL SERVICE METADATA PUBLISHER TEST CA"), PrincipalNameRule.Principal.ISSUER))
                .addRule(new ChainRule(rootCertificates, intermediateCertificates))
                .addRule(new CRLRule(crlCache))
                .addRule(new OCSPRule(intermediateCertificates))
                .build()
                .validate(getClass().getResourceAsStream("/peppol-test-smp-difi.cer"));
    }

    @Test(enabled = false)
    public void simpleProdAp() throws Exception {
        KeyStoreCertificateBucket keyStoreCertificateBucket = new KeyStoreCertificateBucket(getClass().getResourceAsStream("/peppol-prod.jks"), "peppol");
        CertificateBucket rootCertificates = keyStoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keyStoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        ValidatorBuilder.newInstance()
                .addRule(new ExpirationRule())
                .addRule(new SigningRule())
                .addRule(new PrincipalNameRule("CN", new SimplePrincipalNameProvider("PEPPOL ACCESS POINT CA"), PrincipalNameRule.Principal.ISSUER))
                .addRule(new ChainRule(rootCertificates, intermediateCertificates))
                .addRule(new CRLRule(crlCache))
                .addRule(new OCSPRule(intermediateCertificates))
                .build()
                .validate(getClass().getResourceAsStream("/peppol-prod-ap-difi.cer"));
    }

    @Test(enabled = false)
    public void simpleProdSmp() throws Exception {
        KeyStoreCertificateBucket keyStoreCertificateBucket = new KeyStoreCertificateBucket(getClass().getResourceAsStream("/peppol-prod.jks"), "peppol");
        CertificateBucket rootCertificates = keyStoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keyStoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        ValidatorBuilder.newInstance()
                .addRule(new ExpirationRule())
                .addRule(new SigningRule())
                .addRule(new PrincipalNameRule("CN", new SimplePrincipalNameProvider("PEPPOL SERVICE METADATA PUBLISHER CA"), PrincipalNameRule.Principal.ISSUER))
                .addRule(new ChainRule(rootCertificates, intermediateCertificates))
                .addRule(new CRLRule(crlCache))
                .addRule(new OCSPRule(intermediateCertificates))
                .build()
                .validate(getClass().getResourceAsStream("/peppol-prod-smp-difi.cer"));
    }
}

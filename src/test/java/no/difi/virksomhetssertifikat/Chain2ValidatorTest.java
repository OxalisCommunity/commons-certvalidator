package no.difi.virksomhetssertifikat;

import no.difi.virksomhetssertifikat.api.CertificateBucket;
import no.difi.virksomhetssertifikat.util.KeystoreCertificateBucket;
import org.testng.annotations.Test;

public class Chain2ValidatorTest {

    @Test
    public void simple() throws Exception {
        KeystoreCertificateBucket keystoreCertificateBucket = new KeystoreCertificateBucket("JKS", getClass().getResourceAsStream("/peppol-test.jks"), "peppol");
        CertificateBucket rootCertificates = keystoreCertificateBucket.toSimple("peppol-root");
        CertificateBucket intermediateCertificates = keystoreCertificateBucket.toSimple("peppol-ap", "peppol-smp");

        ValidatorHelper validator = ValidatorBuilder.newInstance()
                .append(new Chain2Validator(rootCertificates, intermediateCertificates))
                .build();

        validator.validate(getClass().getResourceAsStream("/peppol-test-ap-difi.cer"));
        validator.validate(getClass().getResourceAsStream("/peppol-test-smp-difi.cer"));
    }

}

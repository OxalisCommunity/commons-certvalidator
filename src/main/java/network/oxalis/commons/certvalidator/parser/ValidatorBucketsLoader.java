package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.util.KeyStoreCertificateBucket;
import network.oxalis.commons.certvalidator.util.SimpleCertificateBucket;
import network.oxalis.commons.certvalidator.Validator;
import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.Order;
import network.oxalis.commons.certvalidator.api.ValidatorRecipeParser;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import network.oxalis.commons.certvalidator.jaxb.*;
import org.kohsuke.MetaInfServices;

import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author erlend
 */
@Order(200)
@MetaInfServices
public class ValidatorBucketsLoader implements ValidatorRecipeParser {

    @Override
    public void parse(ValidatorRecipe recipe, Map<String, Object> objectStorage) throws ValidatorParsingException {
        try {
            for (CertificateBucketType certificateBucketType : recipe.getCertificateBucket()) {
                SimpleCertificateBucket certificateBucket = new SimpleCertificateBucket();

                for (Object o : certificateBucketType.getCertificateOrCertificateReferenceOrCertificateStartsWith()) {
                    if (o instanceof CertificateType) {
                        certificateBucket.add(Validator.getCertificate(((CertificateType) o).getValue()));
                    } else if (o instanceof CertificateReferenceType) {
                        CertificateReferenceType c = (CertificateReferenceType) o;
                        for (X509Certificate certificate :
                                getKeyStore(c.getKeyStore(), objectStorage).toSimple(c.getValue()))
                            certificateBucket.add(certificate);
                    } else if (o instanceof CertificateStartsWithType) {
                        CertificateStartsWithType c = (CertificateStartsWithType) o;
                        for (X509Certificate certificate :
                                getKeyStore(c.getKeyStore(), objectStorage).startsWith(c.getValue()))
                            certificateBucket.add(certificate);
                    }
                }

                objectStorage.put(String.format("#bucket::%s", certificateBucketType.getName()), certificateBucket);
            }
        } catch (CertificateValidationException e) {
            throw new ValidatorParsingException(e.getMessage(), e);
        }
    }

    private static KeyStoreCertificateBucket getKeyStore(String name, Map<String, Object> objectStorage) {
        return (KeyStoreCertificateBucket) objectStorage.get(
                String.format("#keyStore::%s", name == null ? "default" : name));
    }
}

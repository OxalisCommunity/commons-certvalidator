package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.util.KeyStoreCertificateBucket;
import network.oxalis.commons.certvalidator.api.CertificateBucketException;
import network.oxalis.commons.certvalidator.api.Order;
import network.oxalis.commons.certvalidator.api.ValidatorRecipeParser;
import network.oxalis.commons.certvalidator.jaxb.KeyStoreType;
import network.oxalis.commons.certvalidator.jaxb.ValidatorRecipe;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * @author erlend
 */
@Order(100)
@MetaInfServices
public class ValidatorKeyStoresLoader implements ValidatorRecipeParser {

    @Override
    public void parse(ValidatorRecipe recipe, Map<String, Object> objectStorage) throws ValidatorParsingException {
        try {
            for (KeyStoreType keyStoreType : recipe.getKeyStore()) {
                objectStorage.put(
                        String.format("#keyStore::%s", keyStoreType.getName() == null ? "default" : keyStoreType.getName()),
                        new KeyStoreCertificateBucket(
                                new ByteArrayInputStream(keyStoreType.getValue()),
                                keyStoreType.getPassword()
                        )
                );
            }
        } catch (CertificateBucketException e) {
            throw new ValidatorParsingException(e.getMessage(), e);
        }
    }
}

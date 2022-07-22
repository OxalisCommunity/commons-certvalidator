package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.CRLRule;
import network.oxalis.commons.certvalidator.util.SimpleCachingCrlFetcher;
import network.oxalis.commons.certvalidator.util.SimpleCrlCache;
import network.oxalis.commons.certvalidator.api.CrlCache;
import network.oxalis.commons.certvalidator.api.CrlFetcher;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.CRLType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class CRLRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return CRLType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        if (!objectStorage.containsKey("crlFetcher") && !objectStorage.containsKey("crlCache"))
            objectStorage.put("crlCache", new SimpleCrlCache());

        if (!objectStorage.containsKey("crlFetcher"))
            objectStorage.put("crlFetcher", new SimpleCachingCrlFetcher((CrlCache) objectStorage.get("crlCache")));

        return new CRLRule((CrlFetcher) objectStorage.get("crlFetcher"));

    }
}

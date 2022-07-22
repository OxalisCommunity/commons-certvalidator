package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.CriticalExtensionRecognizedRule;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.CriticalExtensionRecognizedType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class CriticalExtensionRecognizedRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return CriticalExtensionRecognizedType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        CriticalExtensionRecognizedType rule = (CriticalExtensionRecognizedType) o;

        return new CriticalExtensionRecognizedRule(rule.getValue().toArray(new String[rule.getValue().size()]));
    }
}

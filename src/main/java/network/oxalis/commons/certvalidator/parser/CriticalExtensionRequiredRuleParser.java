package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.CriticalExtensionRequiredRule;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.CriticalExtensionRequiredType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class CriticalExtensionRequiredRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return CriticalExtensionRequiredType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        CriticalExtensionRequiredType rule = (CriticalExtensionRequiredType) o;

        return new CriticalExtensionRequiredRule(rule.getValue().toArray(new String[rule.getValue().size()]));
    }
}

package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.RuleReferenceType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class RuleReferenceRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return RuleReferenceType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        RuleReferenceType ruleReferenceType = (RuleReferenceType) o;

        if (!objectStorage.containsKey(ruleReferenceType.getValue()))
            throw new ValidatorParsingException(
                    String.format("Rule for '%s' not found.", ruleReferenceType.getValue()));

        return (ValidatorRule) objectStorage.get(ruleReferenceType.getValue());

    }
}

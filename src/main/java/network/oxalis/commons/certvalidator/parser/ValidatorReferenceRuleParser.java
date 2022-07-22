package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.ValidatorReferenceType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class ValidatorReferenceRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return ValidatorReferenceType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        ValidatorReferenceType rule = (ValidatorReferenceType) o;

        String identifier = String.format("#validator::%s", rule.getValue());
        if (!objectStorage.containsKey(identifier))
            throw new ValidatorParsingException(String.format("Unable to find validator '%s'.", rule.getValue()));

        return (ValidatorRule) objectStorage.get(identifier);
    }
}

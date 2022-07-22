package network.oxalis.commons.certvalidator.api;

import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;

import java.util.Map;

/**
 * @author erlend
 */
public interface ValidatorRuleParser {

    boolean supports(Class cls);

    ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException;

}

package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.DummyRule;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.DummyType;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class DummyRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return DummyType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) {
        DummyType dummyType = (DummyType) o;

        return new DummyRule(dummyType.getValue());
    }
}


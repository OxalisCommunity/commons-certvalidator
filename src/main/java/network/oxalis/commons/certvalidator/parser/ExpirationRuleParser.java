package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.ExpirationRule;
import network.oxalis.commons.certvalidator.rule.ExpirationSoonRule;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.ExpirationType;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class ExpirationRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return ExpirationType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) {
        ExpirationType expirationType = (ExpirationType) o;

        if (expirationType.getMillis() == null)
            return new ExpirationRule();
        else
            return new ExpirationSoonRule(expirationType.getMillis());

    }
}

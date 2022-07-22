package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.SigningRule;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.SigningEnum;
import network.oxalis.commons.certvalidator.jaxb.SigningType;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class SigningRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return SigningType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) {
        SigningType signingType = (SigningType) o;

        if (signingType.getType().equals(SigningEnum.SELF_SIGNED))
            return SigningRule.SelfSignedOnly();
        else
            return SigningRule.PublicSignedOnly();
    }
}

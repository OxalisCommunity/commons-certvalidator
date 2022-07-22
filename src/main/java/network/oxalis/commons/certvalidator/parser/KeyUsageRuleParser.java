package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.KeyUsageRule;
import network.oxalis.commons.certvalidator.util.KeyUsage;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.KeyUsageEnum;
import network.oxalis.commons.certvalidator.jaxb.KeyUsageType;
import org.kohsuke.MetaInfServices;

import java.util.List;
import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class KeyUsageRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return KeyUsageType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) {
        KeyUsageType keyUsageType = (KeyUsageType) o;

        List<KeyUsageEnum> keyUsages = keyUsageType.getIdentifier();
        KeyUsage[] result = new KeyUsage[keyUsages.size()];

        for (int i = 0; i < result.length; i++)
            result[i] = KeyUsage.valueOf(keyUsages.get(i).name());

        return new KeyUsageRule(result);
    }
}

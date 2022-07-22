package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.rule.PrincipalNameRule;
import network.oxalis.commons.certvalidator.util.SimplePrincipalNameProvider;
import network.oxalis.commons.certvalidator.api.PrincipalNameProvider;
import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.PrincipleNameType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class PrincipleNameRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return PrincipleNameType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        PrincipleNameType principleNameType = (PrincipleNameType) o;

        PrincipalNameProvider<String> principalNameProvider;
        if (principleNameType.getReference() != null)
            principalNameProvider = (PrincipalNameProvider<String>) objectStorage.get(principleNameType.getReference().getValue());
        else
            principalNameProvider = new SimplePrincipalNameProvider(principleNameType.getValue());

        return new PrincipalNameRule(
                principleNameType.getField(),
                principalNameProvider,
                principleNameType.getPrincipal() != null ?
                        PrincipalNameRule.Principal.valueOf(principleNameType.getPrincipal().toString()) : PrincipalNameRule.Principal.SUBJECT
        );
    }
}

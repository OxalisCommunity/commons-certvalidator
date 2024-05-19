package network.oxalis.commons.certvalidator.parser;

import network.oxalis.commons.certvalidator.api.ValidatorRule;
import network.oxalis.commons.certvalidator.api.ValidatorRuleParser;
import network.oxalis.commons.certvalidator.jaxb.ClassType;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;
import org.kohsuke.MetaInfServices;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author erlend
 */
@MetaInfServices
public class ClassRuleParser implements ValidatorRuleParser {

    @Override
    public boolean supports(Class cls) {
        return ClassType.class.equals(cls);
    }

    @Override
    public ValidatorRule parse(Object o, Map<String, Object> objectStorage) throws ValidatorParsingException {
        ClassType classType = (ClassType) o;

        try {
            return (ValidatorRule) Class.forName(classType.getValue()).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ValidatorParsingException(
                    String.format("Unable to load rule '%s'.", classType.getValue()), e);
        }
    }
}

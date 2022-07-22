package network.oxalis.commons.certvalidator.api;

import network.oxalis.commons.certvalidator.jaxb.ValidatorRecipe;
import network.oxalis.commons.certvalidator.lang.ValidatorParsingException;

import java.util.Map;

/**
 * @author erlend
 */
public interface ValidatorRecipeParser {

    void parse(ValidatorRecipe validatorRecipe, Map<String, Object> objectStorage) throws ValidatorParsingException;

}

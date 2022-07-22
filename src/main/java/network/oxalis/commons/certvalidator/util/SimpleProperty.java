package network.oxalis.commons.certvalidator.util;

import network.oxalis.commons.certvalidator.api.Property;

/**
 * @author erlend
 */
public class SimpleProperty<T> implements Property<T> {

    public static <T> Property<T> create() {
        return new SimpleProperty<>();
    }

    private SimpleProperty() {
        // No action.
    }
}

package network.oxalis.commons.certvalidator.util;

import network.oxalis.commons.certvalidator.api.Property;
import network.oxalis.commons.certvalidator.api.Report;

import java.util.Collections;
import java.util.Set;

/**
 * @author erlend
 */
public class DummyReport implements Report {

    public static final DummyReport INSTANCE = new DummyReport();

    private DummyReport() {
        // No action.
    }

    @Override
    public <T> boolean contains(Property<T> key) {
        return false;
    }

    @Override
    public <T> void set(Property<T> key, T value) {
        // No action.
    }

    @Override
    public <T> T get(Property<T> key) {
        return null;
    }

    @Override
    public Set<Property> keys() {
        return Collections.emptySet();
    }

    @Override
    public Report copy() {
        return this;
    }
}

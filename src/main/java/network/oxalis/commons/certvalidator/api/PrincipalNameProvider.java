package network.oxalis.commons.certvalidator.api;

/**
 * Used by PrincipalNameValidator to implement validation logic.
 */
public interface PrincipalNameProvider<T> {
    boolean validate(T value);
}

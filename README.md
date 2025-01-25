# Certificate validator for X.509 certificates

[![commons-certvalidator Master Build](https://github.com/OxalisCommunity/commons-certvalidator/workflows/commons-certvalidator%20Master%20Build/badge.svg?branch=master)](https://github.com/OxalisCommunity/commons-certvalidator/actions?query=workflow%3A%22commons-certvalidator%20Master%20Build%22)
[![Maven Central](https://img.shields.io/maven-central/v/network.oxalis.commons/commons-certvalidator.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22network.oxalis.commons%22%20AND%20a%3A%22commons-certvalidator%22)


This validator is not a single validator, it is set of rules to build the certificate validator (using X.509 certificates) fitting the needs of your business case.

A lot of sensible defaults is used to make it easy to get started using this library. Use a proper IDE to customize to your needs.


## Getting started

Include dependency in your pom.xml:

```xml
<dependency>
    <groupId>network.oxalis.commons</groupId>
    <artifactId>commons-certvalidator</artifactId>
    <version>4.2.1</version>
</dependency>
```

Create your own validator(s):

```java
// Generic validator
Validator validator = ValidatorBuilder.newInstance()
    .addRule(new ExpirationRule())
    .addRule(new SigningRule())
    .addRule(new CRLRule())
    .addRule(new OCSPRule())
    .build();

// Accept only non-expired self-signed certificates
Validator validator = ValidatorBuilder.newInstance()
    .addRule(new ExpirationRule())
    .addRule(SigningRule.SelfSignedOnly())
    .build();

// Is the certificate expiring in less than 7 days?
Validator validator = ValidatorBuilder.newInstance()
    .addRule(new ExpirationSoonRule(7 * 24 * 60 * 60 * 1000))
    .build();
    
// Validate your certificate (throws exception on error)
validator.validate(...);

// Validate your certificate (returns boolean)
validator.isValid(...);
```

Please note the ```Validator``` accepts ```InputStream```, ```byte[]``` and ```X509Certificate``` as input for validation.

Validators may not only be used to judge a given certificate when in situation to trust or not to trust a certificate. A validator instance may be used to implement logic helping users to handle certificates in a better manner (ie. give a warning before certificate expires). 


## Available building blocks

* **ChainRule** - Validates chain of trust of certificate given access to root certificates and intermediate certificates.
* **CriticalExtensionRule** - Validates required or recognized extensions.
* **CRLRule** - Use information regarding Certificate Revocation List (CRL) in certificate to validate certificate.
* **DummyRule** - Very simple implementation potentially interesting to use in testing.
* **ExpirationSoonRule**
* **ExpirationRule**
* **OCSPRule** - Use information regarding Online Certificate Status Protocol (OCSP) in certificate to validate certificate.
* **PrincipalNameRule**
* **SigningRule**


### Structure

* **Junction** - Combine multiple validators into one validator using 'and', 'or' and 'xor'.


### Extras

* **NorwegianOrganizationNumberRule** *(extends PrincipalNameRule)* - Implements logic to fetch a norwegian organization number from a certificate given [standardization](http://www.regjeringen.no/upload/FAD/Vedlegg/IKT-politikk/SEID_Leveranse_1_-_v1.02.pdf) is used.


## Exceptions

* **CertificateValidatorException** - This is thrown if anything around validation of certificate results in problems.
* **FailedValidationException** *(extends CertificateValidatorException)* - This is thrown when certificate is validated to not be valid.
* **CertificateBucketException** *(extends CertificateValidatorException)* - This is thrown when there are problems regarding certificate buckets.


## Creating new rules

All new validation rules must implement the very simple ```ValidatorRule``` interface to be included in a chain of rules.


---
### Note
This implementation is based on Original version from felleslosninger's efm-certvalidator/commons-certvalidator-2.2.1 (https://github.com/felleslosninger/efm-certvalidator)

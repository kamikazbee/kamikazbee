# kamikazbee

[![Build Status](https://travis-ci.org/kamikazbee/kamikazbee.svg?branch=master)](https://travis-ci.org/kamikazbee/kamikazbee) [![Coverage Status](https://coveralls.io/repos/github/kamikazbee/kamikazbee/badge.svg?branch=master)](https://coveralls.io/github/kamikazbee/kamikazbee?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.kamikazbee/kamikazbee/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.kamikazbee/kamikazbee)

Kamikazbee is a java 8 library that serves a single purpose! Validate java objects.

The core object of the library is the `Validator` which takes a list of validation rules that should be applied
and validates a given object. A validation rule is the check to be performed on a given object.
  
Kamikazbee comes with some stock rules but you can also easily create your own custom ones.

## Stock validation rules

### Empty
Validates that a `String` value is not null or an empty (trimmed) `String`

### Length
Validates that a given `String` matches the given criteria regarding its length.

#### Options
  * `min` - the minimum characters for the value to have (inclusive)
  * `max` - the maximum characters for the value to have (inclusive)
  * `exact` - the exact number of characters for the value to be

### NonNull
Validates that a given object is not null

### Quantity
Validates that a given `Number` matches the given criteria

#### Options
  * `min` - the minimum value (inclusive)
  * `max` - the max value (inclusive)
  * `exact` - the exact value

### Pattern
Validates that a given `String` matches a regex pattern (check [Pattern](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html))

#### Options
  * `pattern` - the pattern to be matched
  * `flags` - the regex flags to be used

## Example

For this example lets consider the following very base schema: 

```java
public class Person {
  private String firstName;
  private String lastName;
  private String sex;
  private Address address;
  private List<Pet> pets;
  
  // getters and setters omitted for simplicity

}
```

```java
public class Address {
  private String country;
  private String state;
  private String city;
  private String street;
  private int number;
  
  // getters and setters omitted for simplicity
}
```

```java
public class Pet {
  private String name;
  private String species;
  
  // getters and setters omitted for simplicity
}
```

Now lets suppose we want to make the following validations:

  * Person#firstName should not be empty and if an error happens place it in the "base" field
  * Person#sex must be between 0 and 6 characters if it exists
  * Person#address must not be null and if it is null it should stop the validation
  * Address#city should not be empty with a custom error message
  * Address#number must be greater than or equal to one
  * Pet#name should not be empty
  
We can achieve all the above with the following code:

```java

Person person = createPerson(); // suppose this creates a Person with some data

Validator<Address> addressValidator = new Validator<>()
  .addRule(empty().message("City is required"), Address:getCity, "city")
  .addRule(quantity().min(1), Address::getNumber, "number");
  
Validator<Pet> petValidator = new Validator<>()
  .addRule(empty(), Pet::getName, "name");

ValidationResult result = new Validator<Person>()
  .addRule(empty(), Person::getFirstName)
  .addRule(length().min(0).max(6).optional(true), Person::getSex, "sex")
  .addRule(nonNull().blocking(true), Person::getAddress, "address")
  .addNestedValidator(addressValidator, Person::getAddress, "address")
  .addListValidator(new Validator<Pet>(), Person::getPets, "pets");
  
  
// Lets suppose that in order to keep it simple we are simply printing to console
if (result.isValid()) {
  System.out.println("Yay, our data are valid!!");
} else {
  System.out.println("Your data are not valid:");
  
  // This will return a Map with the validation errors
  System.out.println(result.getErrors());
}

```

## Create your own validation rule
Well that is all good but what happens if I want to add some custom validation logic? What if I want to validate that the Address number field is a multiple of 3?  We can create a new validation rule for that as follows:

```java
public class MultipleOfThreeRule extends RuleImpl<Integer, MultipleOfThreeRule> {

  MultipleOfThreeRule() {
    message("Must be a multiple of 3"); // Add a default message
  }
  
  @Override
  protected Predicate<Integer> getValidation() {
    return i -> i % 3 == 0;
  }
  
}

```

Great!! You now have a custom `Rule` that check whether an Integer is a multiple of 3 that can be used like:

```java
   ...
   .addRule(new MultipleOfThreeRule(), Address:getNumber, "number");
```

But wait what happens if I want to make my rule more generic and pass the number for the mod as an option? Then you have to make your `Rule` a bit different, like this:

```java
public class MultipleOfRule extends RuleImpl<Integer, MultipleOfRule> {

  private int number;
  
  public MultipleOfRule number(int number) {
    this.number = number;
    return this;
  }
  
  @Override
  protected Predicate<Integer> getValidation() {
    return i -> i % number == 0;
  }
  
  @Override
  protected String getMessage() {
    // Get the message from super in order to return a custom message if given instead of the default
    String superMessage = super.getMessage();
    if(superMessage != null) {
      return superMessage;
    } else {
      return "Must be a multiple of " + number;
    }
  }
  
}

```

You can then do the following:

```java
   ...
   .addRule(new MultipleOfRule().number(5), Address:getNumber, "number");
```

## Download

We are on [Maven Central](http://search.maven.org/#artifactdetails%7Ccom.github%7Ckamikazbee%7C0.1.0%7Cjar).

**Use with gradle**
```gradle
compile 'com.github:kamikazbee:0.1.0'
```

**Use with maven**
```xml
<dependency>
    <groupId>com.github</groupId>
    <artifactId>kamikazbee</artifactId>
    <version>0.1.0</version>
</dependency>
```
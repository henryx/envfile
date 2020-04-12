# EnvFile
A [12-factor](https://12factor.net/config) Java API configuration library

## Install

With maven, you should compile with:
```
mvn clean install
```

After compiled, you can add it in your `pom.xml` these lines:
```xml
<dependency>
  <groupId>org.library</groupId>
  <artifactId>envfile</artifactId>
  <version>0.1.2</version>
</dependency>
```

## Usage

Suppose you have an `.env` file in your project like this:
```.env
VAR1="this is a value"
VAR2=123
```

you can load it with this code:
```java
import org.library.envfile.EnvFile;

public class Example {
    public static void main(String... args) {
        EnvFile.load();
        System.out.println(System.getenv("VAR1"));
        System.out.println(System.getenv("VAR2"));
    }
}
```

## Notes

This library is written with help of [this issue of StackOverflow](https://stackoverflow.com/questions/318239/how-do-i-set-environment-variables-from-java).
This means that when you use this library, you might receive this warning:
```
WARNING: An illegal reflective access operation has occurred
```
To suppress this warning, use `--illegal-access=warn` as JVM option. Sadly, at the moment, I cannot kno another method to
avoid it
# RegexJava

RegexJava is a simple regular expression engine implemented in Java. It serves as an educational tool to deepen the understanding of Java and the inner workings of regex engines.

### Overview

This project demonstrates the implementation of a basic regex engine. It parses regular expressions, generates an Abstract Syntax Tree (AST), and compiles this tree into a state machine. The resulting state machine is then executed by the Engine class to match input strings against the specified patterns.

By now the only supported features of the engine are:

- [x] '|' union
- [x] '\*' star
- [x] '()' group
- [x] '+' plus
- [x] '?' question
- [x] '.' any
- [ ] '^' init
- [ ] '$' eos
- [ ] '[]' set
- [ ] '-' range

### Compilation

This project uses the Maven build system, to compile the program to a .jar you can execute the following command:

```sh
mvn package -f pom.xml
```

Or just use the build system of your IDE.

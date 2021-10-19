[![Build Status](https://travis-ci.com/theKayani/hklib.svg?branch=main)](https://travis-ci.com/theKayani/hklib)
[![Maven Central](https://img.shields.io/maven-central/v/com.thekayani/hklib.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.thekayani%22%20AND%20a:%22hklib%22)
[![javadoc](https://javadoc.io/badge2/com.thekayani/hklib/javadoc.svg)](https://javadoc.io/doc/com.thekayani/hklib)


# hklib

This is a collection of useful classes and functionalities that I found myself in need of over my years as a developer.

##### Version: 1.1.1

### Current Features
 - Extensive and customizable `LuaInterpreter` with strong Java integration
 - Fully featured JSON reader and writer (_w_ Object-Oriented serialization)
 - Various 2D/3D Matrix, Vector, Point, and Quaternion classes
 - A myriad of file helpers such as:
   - `FileUtil` - contains multiple methods to read/write and handle general files and folders
   - `ZipUtil` - contains functions to pack/extract ZIP or JAR files
   - `DataTag` - useful class for de/serializing Java objects to be written-to or read-from a file
   - `IniUtil` - read and write INI files
 - `java.util.Collection` helper classes as well as custom subclasses for collection classes
   - Lists: `LockableList`, `SortedList`, `ImmutableList`, `ListUtil`
   - Maps: `MapUtil`
   - Collections: `ComparatorUtil`, `CollectionUtil`
 - Math and Number utility classes to easily manipulate all types of primitive data
 - The `AlgebraicExpression` class which can read linear and quadratic expressions
   - Such as `x^2 + 5x - 3` and allow the user to plug in values
 - Super Light and Simple Neural Network implementation with full saving and loading capabilities
 - **Continuous Integration** to test all types of functions and methods contained within this library
   - Tests can be accessed over at [https://travis-ci.com/github/theKayani/hklib](https://travis-ci.com/github/theKayani/hklib)
 - _Available with various builders or as a flat JAR file library through Maven Central_

## Usage

#### Reference
You can access the Javadoc over at [https://javadoc.io/doc/com.thekayani/hklib](https://javadoc.io/doc/com.thekayani/hklib)

The process to use it is very simple and are similar to each other.
Simply add the library/dependency to your build file.

### Maven
Add `hklib` dependency

    <dependency>
        <groupId>com.thekayani</groupId>
        <artifactId>hklib</artifactId>
        <version>1.1.1</version>
    </dependency>

### Gradle
Add `hklib` dependency

    dependencies {
	        implementation 'com.thekayani:hklib:1.1.1'
	}

### Flat `jar` File
You have access to the `jar` file with the compiled code and sources under GitHub releases.
You can download the `jar` file [here](https://search.maven.org/artifact/com.thekayani/hklib).
This can be used directly on the command line when executing a Java program using
the `-classpath` flag with the `java` command. Or added into your own project in various
IDEs.

#### Others
You can explore the other ways over at https://search.maven.org/artifact/com.thekayani/hklib

## Development

To develop this library further, you can clone the repo and use Maven to
import this project into the major Java IDEs such as Eclipse, Netbeans, and IntelliJ IDEA

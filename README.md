[![Build Status](https://travis-ci.com/theKayani/hklib.svg?branch=main)](https://travis-ci.com/theKayani/hklib)
[![](https://jitpack.io/v/thekayani/hklib.svg)](https://jitpack.io/#com.thekayani/hklib)

# hklib

This is a collection of useful classes and functionalities that I found myself in need of over my years as a developer.

##### Version: 1.0.4

## Usage

#### Reference
You can access the Javadoc over at https://javadoc.jitpack.io/com/github/theKayani/hklib/latest/javadoc/

The process to use it is very simple and are similar to each other.
Add the repository, and add the library/dependency

### Maven
Add JitPack repository to `pom.xml`

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

Add `hklib` dependency

    <dependency>
        <groupId>com.thekayani</groupId>
        <artifactId>hklib</artifactId>
        <version>$VERSION</version>
    </dependency>

### Gradle
Add to root `build.gradle`

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

Add `hklib` dependency

    dependencies {
	        implementation 'com.thekayani:hklib:VERSION'
	}

### Flat `jar` File
You have access to the `jar` file with the compiled code and sources under GitHub releases.
You can download the `jar` file [here](https://github.com/theKayani/hklib/releases).
This can be used directly on the command line when executing a Java program using
the `-classpath` flag with the `java` command. Or added into your own project in various
IDEs.

#### Others
You can explore the other ways over at https://jitpack.io/#com.thekayani/hklib.
This includes Scala's SBT and Leiningen.

## Development

To develop this library further, you can clone the repo and use Maven to
import this project into the major Java IDEs such as Eclipse, Netbeans, and IntelliJ IDEA

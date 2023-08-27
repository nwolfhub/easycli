# EasyCLI

## Introduction

EasyCLI is a library made to read user input and print in console in a simplified way.

To add an EasyCLI to your project, simply append

```xml
<dependency>
    <groupId>org.nwolfhub</groupId>
    <artifactId>easycli</artifactId>
    <version>1.4.4</version>
</dependency>
```

into your dependencies section of pom.xml

**Now lets get started!**

## Simple Hello world

First we need to create an instance of EasyCLI:

```java
EasyCLI cli = new EasyCLI(); //by default system's input and output stream will be used
```

Then we can use it to print into a console:

```java
cli.print("Hello, World!");
```

## Templates

### Default

```java
EasyCLI cli = new EasyCLI(); //creating a new instance
cli.addTemplate(Defaults.defaultTemplate); //adding a default template with \r at the end
cli.print("Hello, World!");
Thread.sleep(1000);
cli.print("And it walks away");
```

just a normal print, nothing special

### Carriage return

Now let's customize our text for a bit. Lets use carriage return (\r) to upgrade our printing!

```java
EasyCLI cli = new EasyCLI(); //creating a new instance
cli.addTemplate(Defaults.carriageReturn); //adding a default template with \r at the end
cli.print("Hello, World!");
Thread.sleep(1000);
cli.print("And it walks away");
```

In console first "Hello, World!" will appear and then will be replaced with "And it walks away" after a second.

### Boxed text

```java
EasyCLI cli = new EasyCLI(); //creating a new instance
cli.addTemplate(Defaults.boxedText); //adding a default template with \r at the end
cli.print("Hello, World!");
Thread.sleep(1000);
cli.print("And it walks away");
```

This will print a text in a box

## Handling input

First, create a new EasyCLI instance:

```java
EasyCLI cli = new EasyCLI();
```

Now add a command it should listen for and write a function for it:

```java
cli.addTask("help", new InputTask() {
    @Override
    public void act(EasyCLI cli, String... params) {
        cli.print("This program does not have a help page");
    }
});
```

Now when a user types "help" in the console your function will be called. "cli" parameter will be set by EasyCLI instance that has called this method

# Introduction

Allan Gray developer interview assignment. Requires a file to be read, some process to take place and output a report.

# Business Requirements
Please write a program to solve the following simple problem. Your program will receive multiple filenames as command line parameters. 

Each file should have a header row (productid, unit price, description) followed by a number of lines with comma separated values. Furthermore, a well-formed file will have a value for each column and the values will be of the correct type. Given the values of the individual invoice items, write a single summary to the console in the same format as the one depicted in the example below.

Example 1:

Given the file attached (invoiceItems.txt), the program will generate the following output in CSV format, the output can be printed directly to console…no need to save into a file.

The content of the output file should look something like the following:

|Product Id|Description|Count|Unit Price|Vat|Line Total|
| :------------- | :------------- | -------------: | -------------: | -------------: | -------------: |
|1|6 pack-tumblers|2|R80.00|R11.20|R182.40|
|2|10 pack-swizzle sticks|4|R5.00|R0.70|R22.80|
|3|Bag of ice|5|R10|R1.40|R57|
|4|2l Coke|2|R20|R2.80|R45.60|
|5|2l Sprite|2|R18.50|R2.59|R42.18|
|Total||||R18.69|R349.98|

# Software Requirements 

* **Gradle 1.11** is required to build the project. It can be found on: www.gradle.org. Follow their instructions for installation.
* **Java 7 Update 51** is required to build the project. The JDK can be found on: http://www.oracle.com/technetwork/java/javase/downloads/index.html Follow their instructions for installation.

# Assumptions

* No VAT amount has been specified, however using the example values we can assume the VAT rate is 14%.
* This is being built on a **linux** environment.
* Use system region for determining currency and language to support internationalization.

# Building

To build and run the tests use the following:

```Shell
    gradle build 
```

To run code coverage:

```Shell
    gradle build jTR
```

To create a distribution:

```Shell
    gradle build distZip
```

# Usage

Once the distribution has been build you can navigate to:

```
    cd build/distribution
```

There should be a file called **allangray-assignment-1.0.0.zip** present in the directory. Unzip the file:

```
    unzip allangray-assignment-1.0.0.zip
```

And navigate to:

```
    cd allangray-iview/bin
```

To run the shell script:

```
    ./run.sh ../../../path/to/file.txt
```



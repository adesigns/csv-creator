
# CSV Creator
A super-simple library to generate CSVs from POJOs.  Allows you to define the expected output of a CSV file by creating a POJO marked with annotations.

[![Build Status](https://img.shields.io/travis/adesigns/csv-creator.svg)](https://travis-ci.org/adesigns/csv-creator)
[![Code Coverage](https://img.shields.io/coveralls/adesigns/csv-creator.svg)](https://coveralls.io/github/adesigns/csv-creator)


## Installation

### via Maven
Add the dependency to your pom.xml:

```xml
<dependency>
  <groupId>com.adesigns</groupId>
  <artifactId>csv-creator</artifactId>
  <version>1.0.0</version>
</dependency>
```
### via Gradle

Add the dependency to your build.gradle:

```groovy
compile 'com.adesigns:csv-creator:1.0.0'
```

## Basic Usage

### 1. Create a POJO that will represent your CSV:

```java
package com.somecompany.csv.pojo;

import com.adesigns.csv.annotation.CsvColumn;
import java.util.Date;

public class CsvPojo {

    @CsvColumn(title = "String Column")
    private String stringColumn;

    @CsvColumn(title = "Integer Column")
    private Integer integerColumn;

    @CsvColumn(title = "Date Column", dateFormat="MM/dd/yyyy") // dateFormat is optional.  See CsvColumn for default format.
    private Date dateColumn;

    public String getStringColumn() {
        return stringColumn;
    }

    public void setStringColumn(String stringColumn) {
        this.stringColumn = stringColumn;
    }

    public Integer getIntegerColumn() {
        return integerColumn;
    }

    public void setIntegerColumn(Integer integerColumn) {
        this.integerColumn = integerColumn;
    }

    public Date getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(Date dateColumn) {
        this.dateColumn = dateColumn;
    }
}
```

### 2. Create some POJOs and Write to XML

```java

Pojo firstPojo = new Pojo();
firstPojo.setStringColumn("String 1");
firstPojo.setIntegerColumn(1234);
firstPojo.setDateColumn(new Date());

Pojo secondPojo = new Pojo();
secondPojo.setStringColumn("String 2");
secondPojo.setDateColumn(new Date());

List<Pojo> pojoList = new LinkedList<Pojo>();
pojoList.add(firstPojo);
pojoList.add(secondPojo);
```

```java
final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

CsvCreator<Pojo> csvCreator = new CsvCreator<Pojo>(new OutputStreamWriter(outputStream), Pojo.class);

csvCreator.write(pojoList);

writer.close();

byte[] csvOutput = outputStream.toByteArray();
```

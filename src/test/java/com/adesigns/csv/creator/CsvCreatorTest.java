/*
 * Copyright [2016] Michael Yudin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.adesigns.csv.creator;

import com.adesigns.csv.mock.Pojo;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CsvCreatorTest {

    @Test
    public void testWriteCsvWithoutHeaders() throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        CsvCreator<Pojo> csvCreator = new CsvCreator<Pojo>(writer, Pojo.class);

        List<Pojo> pojoList = getPojoList();

        csvCreator.write(pojoList);

        writer.close();

        final String output = new String(outputStream.toByteArray(), "UTF-8");

        String[] rows = output.split("\n");

        Assert.assertEquals(2, rows.length);

        String[] firstRow = rows[0].split(",");
        Assert.assertEquals("String 1", firstRow[0]);
        // Assert.assertEquals(pojoList.get(0).getDateColumn().toString(), firstRow[2]);


        String[] secondRow = rows[1].split(",");
        Assert.assertEquals("String 2", secondRow[0]);
        //Assert.assertEquals(pojoList.get(1).getDateColumn().toString(), secondRow[2]);
    }

    @Test
    public void testWriteRowRequiringEscape() throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        CsvCreator<Pojo> csvCreator = new CsvCreator<Pojo>(writer, Pojo.class);

        Pojo pojo = new Pojo();
        pojo.setStringColumn("I require, \"\n\rquotes!");

        csvCreator.writeRow(pojo);

        writer.close();

        final String output = new String(outputStream.toByteArray(), "UTF-8");
        Assert.assertEquals("\"" + pojo.getStringColumn() + "\",,\n", output);
    }

    @Test
    public void testWriteHeaders() throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        CsvCreator<Pojo> csvCreator = new CsvCreator<Pojo>(writer, Pojo.class);

        csvCreator.writeHeaders();
        writer.close();

        final String output = new String(outputStream.toByteArray(), "UTF-8");

        String[] rows = output.split("\n");

        Assert.assertEquals(1, rows.length);

        String[] firstRow = rows[0].split(",");

        Assert.assertEquals("String Column", firstRow[0]);
        Assert.assertEquals("Integer Column", firstRow[1]);
        Assert.assertEquals("Date Column", firstRow[2]);
    }

    private List<Pojo> getPojoList() {

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

        return pojoList;
    }
}

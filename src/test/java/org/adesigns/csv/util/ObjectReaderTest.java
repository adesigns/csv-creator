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

package org.adesigns.csv.util;

import org.adesigns.csv.mock.Pojo;
import org.adesigns.csv.util.ObjectReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ObjectReaderTest {

    @Test
    public void testReadHeaderRow() {
        Pojo pojo = new Pojo();

        List<String> headerColumns = ObjectReader.getHeaderColumns(Pojo.class);

        Assert.assertEquals(Pojo.class.getDeclaredFields().length, headerColumns.size());
        Assert.assertEquals("String Column", headerColumns.get(0));
        Assert.assertEquals("Integer Column", headerColumns.get(1));
        Assert.assertEquals("Date Column", headerColumns.get(2));
    }

    @Test
    public void testReadRow() throws IllegalAccessException {
        Pojo pojo = createPojo();

        List<Object> rowColumns = ObjectReader.getRow(pojo);

        Assert.assertEquals(Pojo.class.getDeclaredFields().length, rowColumns.size());
        Assert.assertEquals("I am a string.", rowColumns.get(0));
        Assert.assertEquals(123, rowColumns.get(1));
    }

    @Test
    public void testReadRowWithEmptyColumn() throws IllegalAccessException {
        Pojo pojo = createPojo();

        pojo.setStringColumn(null);

        List<Object> rowColumns = ObjectReader.getRow(pojo);

        Assert.assertEquals(Pojo.class.getDeclaredFields().length, rowColumns.size());
        Assert.assertEquals("", rowColumns.get(0));
        Assert.assertEquals(123, rowColumns.get(1));
    }

    private Pojo createPojo() {
        Pojo pojo = new Pojo();

        pojo.setStringColumn("I am a string.");
        pojo.setIntegerColumn(123);

        return pojo;
    }
}

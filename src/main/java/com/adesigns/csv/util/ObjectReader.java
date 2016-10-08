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

package com.adesigns.csv.util;

import com.adesigns.csv.annotation.CsvColumn;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectReader {

    /**
     * Returns a List representing a row of header columns.
     *
     * @param pojoClass Class we want to retrieve header columns for.
     * @return List representing a row of header columns.
     */
    public static List<String> getHeaderColumns(Class<?> pojoClass) {
        List<String> headerColumns = new LinkedList<String>();

        for (Field field : pojoClass.getDeclaredFields()) {
            CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);

            headerColumns.add(csvColumn.title());
        }

        return headerColumns;
    }

    /**
     * Returns a List object containing row data.
     *
     * @param pojo Object to convert to a row.
     * @return List of column data for a row
     * @throws IllegalAccessException If a field cannot be accessed via reflection
     */
    public static List<Object> getRow(Object pojo) throws IllegalAccessException {
        List<Object> rowColumns = new LinkedList<Object>();

        for (Field field : pojo.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(CsvColumn.class)) {
                continue;
            }

            field.setAccessible(true);

            // add blank data if field is null
            if (field.get(pojo) == null) {
                rowColumns.add("");
            } else {
                rowColumns.add(formatField(field, pojo));
            }
        }

        return rowColumns;
    }

    /**
     * Format the field depending on the type
     *
     * (IE if it's a date column, format it as specified in the dateFormat property of the CsvColumn annotation).
     *
     * @param field Field on the pojo we want to format.
     * @param pojo Object we want to retrieve field from.
     *
     * @return Formatted field value.
     * @throws IllegalAccessException If the field cannot be accessed via reflection.
     */
    private static Object formatField(Field field, Object pojo) throws IllegalAccessException {

        if (field.get(pojo) instanceof Date) {
            if (field.isAnnotationPresent(CsvColumn.class)) {
                CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat(csvColumn.dateFormat());

                return dateFormat.format(field.get(pojo));
            }
        }

        return field.get(pojo);
    }
}

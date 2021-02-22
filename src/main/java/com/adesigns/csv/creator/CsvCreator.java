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

import com.adesigns.csv.util.ObjectReader;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvCreator<T> {

    private static final char CSV_DELIMITER = ',';

    private static final char CSV_QUOTE = '"';

    private Writer writer;

    private Class<T> clazz;

    /**
     * @param writer Writer to write the CSV output to.
     * @param clazz The Class that contains the CSV metadata.
     */
    public CsvCreator(final Writer writer, Class<T> clazz) {
        this.writer = writer;
        this.clazz = clazz;
    }

    /**
     * Write a list of rows to the output.
     *
     * @param csvRows A List containing the rows to write.
     *
     * @throws IllegalAccessException If an object cannot be read via reflection.
     * @throws IOException If there is an issue writing to the writer.
     */
    public void write(List<T> csvRows) throws IOException, IllegalAccessException {
        for (T row : csvRows) {
            writeRow(row);
        }
    }

    /**
     * Create a header row in the writer.
     *
     * @throws IOException  If there is an issue writing to the writer.
     */
    public void writeHeaders() throws IOException {
        List<String> headerColumns = ObjectReader.getHeaderColumns(clazz);
        StringBuilder row = new StringBuilder();

        boolean first = true;

        for (String column : headerColumns) {
            if (!first) {
                row.append(",");
            }

            row.append(escapeCsvValue(column));

            first = false;
        }

        row.append("\n");

        writer.write(row.toString());
    }

    /**
     * Write row to the writer based on the object passed.
     *
     * @param csvObject The object that should be written to the writer.
     *
     * @throws IllegalAccessException If an object cannot be read via reflection.
     * @throws IOException If there is an issue writing to the writer.
     */
    public void writeRow(T csvObject) throws IllegalAccessException, IOException {
        List<Object> rowColumns = ObjectReader.getRow(csvObject);
        StringBuilder row = new StringBuilder();

        boolean first = true;

        for (Object column : rowColumns) {
            if (!first) {
                row.append(",");
            }

            row.append(escapeCsvValue(String.valueOf(column)));

            first = false;
        }

        row.append("\n");

        writer.write(row.toString());
    }

    /**
     * Prepare a value for CSV output.
     *
     * @param value Unescaped value
     * @return Escaped value
     */
    private String escapeCsvValue(String value) {

        String result = value;

        if (requiresCsvEscape(result)) {
            result = StringEscapeUtils.escapeCsv(result);
        }

        return result;
    }

    /**
     * Does a value require escaping?
     *
     * @param value
     * @return
     */
    private boolean requiresCsvEscape(String value) {
        return value != null && (value.contains("\n") || value.contains("\r") || value.indexOf(CSV_DELIMITER) != -1 || value.indexOf(CSV_QUOTE) != -1);
    }


}

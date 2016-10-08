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

package com.adesigns.csv.mock;

import com.adesigns.csv.annotation.CsvColumn;

import java.util.Date;

public class Pojo {

    @CsvColumn(title = "String Column")
    private String stringColumn;

    @CsvColumn(title = "Integer Column")
    private Integer integerColumn;

    @CsvColumn(title = "Date Column")
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

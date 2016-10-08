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

package org.adesigns.csv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //can use in method only.
public @interface CsvColumn {

    /**
     * @return This is what will be displayed in the header row (if using headers).
     */
    String title();

    /**
     *
     * @return If the field is of type Date, you can format it following the SimpleDateFormat pattern.
     *
     *         http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
     */
    String dateFormat() default "MM/dd/yyyy hh:mm:ss a";
}

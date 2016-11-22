/*
 * The Bestory Project
 */

package com.thebestory.android.loader.main;

/**
 * Три возможных результат процесса загрузки данных.
 */
/*
@deprecated class was written Oktai
*/

@Deprecated
public enum ResultType {

    /**
     * Данные успешно загружены.
     */
    OK,

    /**
     * Данные не загружены из-за отсутствия интернета.
     */
    NO_INTERNET,

    /**
     * Данные не загружены по другой причине.
     */
    ERROR
}
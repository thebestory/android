package com.thebestory.android.loader.main;

/**
 * Created by Октай on 16.11.2016.
 */

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
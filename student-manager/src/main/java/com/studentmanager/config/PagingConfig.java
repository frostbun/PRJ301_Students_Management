package com.studentmanager.config;

public class PagingConfig {
    public static final int SIZE = 25;

    public static Long pageCountOf(Long total) {
        return (total + SIZE - 1) / SIZE;
    }
}

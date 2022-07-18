package com.studentmanager.config;

public abstract class PagingConfig {
    public static final int SIZE = 25;

    public static Long pageCountOf(Long total) {
        return total == 0 ? 1 : (total + SIZE - 1) / SIZE;
    }
}

package com.studentmanager.dto;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private String error;
    private T response;

    public boolean isError() {
        return error != null;
    }

    public static <T> ServiceResponse<T> error(String error) {
        ServiceResponse<T> ret = new ServiceResponse<>();
        ret.setError(error);
        return ret;
    }

    public static <T> ServiceResponse<T> success(T response) {
        ServiceResponse<T> ret = new ServiceResponse<>();
        ret.setResponse(response);
        return ret;
    }
}

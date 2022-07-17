package com.studentmanager.dto;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private boolean error;
    private String errorMessage;
    private T response;

    public static <T> ServiceResponse<T> error() {
        ServiceResponse<T> ret = new ServiceResponse<>();
        ret.setError(true);
        return ret;
    }

    public static <T> ServiceResponse<T> error(String message) {
        ServiceResponse<T> ret = new ServiceResponse<>();
        ret.setError(true);
        ret.setErrorMessage(message);
        return ret;
    }

    public static <T> ServiceResponse<T> success(T response) {
        ServiceResponse<T> ret = new ServiceResponse<>();
        ret.setError(false);
        ret.setResponse(response);
        return ret;
    }
}

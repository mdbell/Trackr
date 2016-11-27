package com.example.matt1.trackr.api;

/**
 * Created by matt1 on 11/26/2016.
 */

public class Envelope<T> {
    Response response;
    T data;

    protected Envelope() {

    }

    public Response getResponse() {
        return response;
    }

    public T getData() {
        return data;
    }

    public static class Response {
        private int code;
        private String title;
        private String message;

        protected Response() {
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getTitle() {
            return title;
        }
    }
}

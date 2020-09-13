package com.github.saphyra.apphub.app.web;

public interface ResponseHandler<T> {
    void handle(T response);
}

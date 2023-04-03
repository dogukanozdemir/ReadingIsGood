package com.books.readingisgood.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class ReadingIsGoodException extends ResponseStatusException {

    @Nullable
    private final String title;

    public ReadingIsGoodException(HttpStatusCode status,String title, String reason) {
        super(status, reason);
        this.title = title;
    }

    @Nullable
    public String getTitle() {
        return this.title;
    }
}

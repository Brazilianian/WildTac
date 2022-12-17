package com.wildtac.exception.alreadyexists;

public class CategoryAlreadyExistsException extends EntityAlreadyExistsException{
    public CategoryAlreadyExistsException() {
        super();
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}

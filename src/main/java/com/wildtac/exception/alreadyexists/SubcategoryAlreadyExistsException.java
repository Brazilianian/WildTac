package com.wildtac.exception.alreadyexists;

public class SubcategoryAlreadyExistsException extends EntityAlreadyExistsException{
    public SubcategoryAlreadyExistsException() {
        super();
    }

    public SubcategoryAlreadyExistsException(String message) {
        super(message);
    }
}

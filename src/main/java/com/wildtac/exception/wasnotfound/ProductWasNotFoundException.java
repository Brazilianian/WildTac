package com.wildtac.exception.wasnotfound;

public class ProductWasNotFoundException extends EntityWasNotFoundException{
    public ProductWasNotFoundException() {
        super();
    }

    public ProductWasNotFoundException(String message) {
        super(message);
    }
}

package dev.kisser.exceptions;

public class DependencyNotFoundException extends Exception{
    public DependencyNotFoundException(String message){
        super(message);
    }
}

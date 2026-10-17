package ch.hslu.sofeng.campuspay_jpa.common.exception;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

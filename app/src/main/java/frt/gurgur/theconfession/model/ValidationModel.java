package frt.gurgur.theconfession.model;

public class ValidationModel {

    public boolean isValid;
    public String errorMessage;

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

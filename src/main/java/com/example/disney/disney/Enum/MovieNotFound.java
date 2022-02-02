package com.example.disney.disney.Enum;

public enum MovieNotFound {

    IdMovie(0,"Movie id not found"),
    FilterCharacter(1,"No movie found with the indicated parameters");

    private final int code;
    private final String description;

    MovieNotFound(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return code+ ": "+ description;
    }
}

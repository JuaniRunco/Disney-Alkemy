package com.example.disney.disney.Enum;

public enum CharacterNotFound {

    IdCharacter(0,"Character id not found"),
    FilterCharacter(1,"No character found with the indicated parameters");

    private final int code;
    private final String description;

    CharacterNotFound(int code, String description) {
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

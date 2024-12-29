package project.library.enums;

import lombok.Getter;

@Getter
public enum Operator
{
    EQUALS("="),
    LIKE("LIKE");

    private final String value;

    Operator(String value){
        this.value = value;
    }

}

package project.library.enums;

import lombok.Getter;

@Getter
public enum Operator
{
    EQUALS("="),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    IN("IN"),
    LIKE("LIKE");

    private final String value;

    Operator(String value){
        this.value = value;
    }

}

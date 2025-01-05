package project.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericReturn
{
    private Object data;

    private String error;

    private int code;

    private String msg;
}

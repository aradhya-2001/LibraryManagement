package project.library.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
/*
Without @EqualsAndHashCode, if 2 rows/object have equal id and email, then they would be considered unequal and will have different hashcode.
That's because their memory address will be different.
So every object will be considered different.
A key for a row has to be unique. So if 2 rows have the same id (x) and email (y), then they should be considered as equal because they have the same keys (x+y) and keys should be unique.
*/
public class AuthorCompositeKey // More scalable way to create Composite key
{
    private String id;
    private String email;
}

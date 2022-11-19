package day20220116;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    String id;
    String sex;
    int age;
}

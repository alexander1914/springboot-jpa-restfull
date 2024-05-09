package io.github.alexander1914.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CredenciaisDTO {
    private String login;
    private String senha;
}

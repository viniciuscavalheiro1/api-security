package br.com.vinicius.clientsecurity.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "client")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "fone_movel", nullable = true, length = 20)
    private String foneMovel;

    @Column(name = "fone_fixo", nullable = true, length = 20)
    private String foneFixo;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
}

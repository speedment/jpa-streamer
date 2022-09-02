package com.speedment.jpastreamer.fieldgenerator.test;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User2  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shared")
    @SequenceGenerator(name = "seq_shared", sequenceName = "seq_shared",allocationSize = 1)
    Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    // Should generate isEnabled 
    private boolean enabled;
    
    // Should generate getActive 
    private Boolean active;
}

package com.camel.camel.beans;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="name_address")
@NamedQuery(name="fetchAllRows", query = "Select x from NameAddress x")
public class NameAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name="Player_name")
    private String name;
    private String club;
    private int number;

}

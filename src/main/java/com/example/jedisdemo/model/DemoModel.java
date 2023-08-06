package com.example.jedisdemo.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DemoModel implements Serializable {
    private long id;
    private String name;
}

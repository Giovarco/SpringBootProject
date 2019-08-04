package net.mignemi.portfolio.model;

import javax.persistence.*;

@Entity
@Table
public class Film  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String locations;

}
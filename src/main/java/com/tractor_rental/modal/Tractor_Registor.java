package com.tractor_rental.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tractors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tractor_Registor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    private String tractorName;
    private String phoneNumber;
    private String model;
    private String licenseNumber;
    private int year;
    private double rentalPrice;
    private String location;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
}

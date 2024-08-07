package com.geek.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data//short cut of @Setter & @Getter
public class Penalty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int penaltyId;
private float amount;
private String remarks;
private int noOfDays;

}

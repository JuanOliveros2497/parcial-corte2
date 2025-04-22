package com.example.backParcial.Models.dao;

import com.example.backParcial.Models.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
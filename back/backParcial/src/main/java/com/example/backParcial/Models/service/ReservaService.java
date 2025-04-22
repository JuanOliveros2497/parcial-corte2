package com.example.backParcial.Models.service;

import com.example.backParcial.Models.dao.ReservaRepository;
import com.example.backParcial.Models.entity.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;

@Service
@Transactional
public class ReservaService {
    @Autowired
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public Reserva crear(Reserva reserva) {
        try {
            return reservaRepository.save(reserva);  // Intenta guardar la reserva y se maneja el control de concurrencia optimista.
        } catch (ObjectOptimisticLockingFailureException e) {
            // Captura el error de bloqueo optimista cuando la fila ha sido modificada por otra transacción.
            System.out.println("Error de bloqueo optimista: " + e.getMessage());
            // Lanza una excepción personalizada o maneja el error según sea necesario.
            throw e; // O puedes lanzar una excepción personalizada aquí si lo prefieres.
        }
    }

    public Reserva actualizar(Long id, Reserva nuevaReserva) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setNombreCliente(nuevaReserva.getNombreCliente());
            reserva.setFecha(nuevaReserva.getFecha());
            reserva.setHora(nuevaReserva.getHora());
            reserva.setMesaId(nuevaReserva.getMesaId());
            return reservaRepository.save(reserva);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}

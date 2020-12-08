package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.TableEntity;
import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.repositories.TableRepository;
import com.restaurant.springbootds.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

    private TableRepository tableRepository;
    private TicketRepository ticketRepository;

    @Override
    public TableEntity createTable(TableEntity table) {
        return this.tableRepository.save(table);
    }

    @Override
    public List<TableEntity> readTables() {
        return this.tableRepository.findAll();
    }

    @Override
    public TableEntity getTableByNum(int numero) {
        TableEntity table = this.tableRepository.findById(numero).orElse(null);
        if (table != null) {
            return table;
        }
        throw new NoSuchElementException("Table number '" + numero + "' does not exist.");
    }

    @Override
    public TableEntity updateTable(int numero, TableEntity table) {
        Optional<TableEntity> theTable = this.tableRepository.findById(numero);
        if (theTable.isPresent()) {
            TableEntity oldTable = theTable.get();
            if (table.getNbCouvert() != null)
                oldTable.setNbCouvert(table.getNbCouvert());
            if (table.getSupplement() != 0)
                oldTable.setSupplement(table.getSupplement());
            if (table.getType() != null)
                oldTable.setType(table.getType());
            return this.tableRepository.save(oldTable);
        }
        throw new NoSuchElementException("Table does not exist.");
    }

    @Override
    public TableEntity deleteTable(int numero) {
        Optional<TableEntity> theTable = this.tableRepository.findById(numero);
        if (theTable.isPresent()) {
            TableEntity table = theTable.get();
            this.tableRepository.deleteById(numero);
            return table;
        }
        throw new NoSuchElementException("Table does not exist.");
    }

    @Override
    public TableEntity mostBookedTable() {
        return this.ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(TicketEntity::getTable, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

package com.restaurant.springbootds.controllers;

import com.restaurant.springbootds.models.TableEntity;
import com.restaurant.springbootds.services.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/table")
@AllArgsConstructor
public class TableController {

    private TableService tableService;

    @PostMapping
    public TableEntity createTable(@RequestBody TableEntity table){
        return this.tableService.createTable(table);
    }

    @GetMapping
    public List<TableEntity> getTables() {
        return this.tableService.readTables();
    }

    @GetMapping("/{numero}")
    public TableEntity getTableByNum(@PathVariable("numero") int numero) {return this.tableService.getTableByNum(numero);}

    @PutMapping("/{numero}")
    public TableEntity updateTable(@PathVariable("numero") int numero, @RequestBody TableEntity table) {
        return this.tableService.updateTable(numero, table);
    }

    @DeleteMapping("/{numero}")
    public TableEntity deleteTable(@PathVariable("numero") int numero) {
        return this.tableService.deleteTable(numero);
    }

    @GetMapping("/mostBooked")
    public TableEntity mostBookedTable() {
        return this.tableService.mostBookedTable();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}


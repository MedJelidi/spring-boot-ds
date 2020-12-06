package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.TableEntity;

import java.util.List;

public interface TableService {
    TableEntity createTable(TableEntity table);
    List<TableEntity> readTables();
    TableEntity updateTable(int numero, TableEntity table);
    TableEntity deleteTable(int numero);
    TableEntity mostBookedTable();
}

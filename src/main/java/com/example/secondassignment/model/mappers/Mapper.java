package com.example.secondassignment.model.mappers;

public interface Mapper<T, V> {

    T convertFromDTO(V v);
    V convertToDTO(T t);
}

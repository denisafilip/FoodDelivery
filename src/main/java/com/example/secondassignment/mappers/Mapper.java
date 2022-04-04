package com.example.secondassignment.mappers;

public interface Mapper<T, V> {

    T convertFromDTO(V v);
    V convertToDTO(T t);
}

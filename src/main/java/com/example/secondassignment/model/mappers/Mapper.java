package com.example.secondassignment.model.mappers;

/**
 * Interface used for converting a DTO to its corresponding model class and vice-versa.
 * @param <T> - Model class
 * @param <V> - DTO class
 */
public interface Mapper<T, V> {

    /**
     * Converts a DTO class to a Model.
     * @param v - DTO object to be converted
     * @return the converted instance of the Model
     */
    T convertFromDTO(V v);

    /**
     * Converts a Model class to a DTO.
     * @param t - Model object to be converted
     * @return the converted instance of the DTO
     */
    V convertToDTO(T t);
}

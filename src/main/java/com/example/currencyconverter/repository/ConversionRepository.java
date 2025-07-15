package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.Conversion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends MongoRepository<Conversion, String> {
}

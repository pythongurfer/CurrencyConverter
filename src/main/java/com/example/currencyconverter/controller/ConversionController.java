package com.example.currencyconverter.controller;


import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //  A combination of @Controller and @ResponseBody, telling Spring this controller handles REST requests and writes the return value directly to the response body.
@RequestMapping("/api/conversions") //  Sets the base URL for all endpoints in this class.
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService){
        this.conversionService = conversionService;
    }

    @PostMapping // Handles POST /api/conversions.
    // @Valid: Triggers the validation rules on our ConversionRequest DTO. If validation fails, Spring automatically sends a 400 Bad Request error.
    // @RequestBody: Tells Spring to map the incoming JSON body to our ConversionRequest object.
    // ResponseEntity: Gives us full control over the HTTP response, including the status code (201 Created).
    // @GetMapping, @DeleteMapping: Handle the other standard REST operations.
    // @PathVariable: Binds the {id} from the URL to the method parameter.
    public ResponseEntity<Conversion> createConversion(@Valid @RequestBody ConversionRequest request){
        Conversion newConversion = conversionService.createConversion(
                request.getSourceCurrency().toUpperCase(),
                request.getTargetCurrency().toUpperCase(),
                request.getAmount()
        );
        return  new ResponseEntity<>(newConversion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Conversion>> getAllConversions(){
        List<Conversion> conversions = conversionService.getAllConversions();
        return ResponseEntity.ok(conversions);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Conversion> getConversionById(@PathVariable String id){
        Conversion conversion = conversionService.getConversionById(id);
        return ResponseEntity.ok(conversion);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversionById(@PathVariable String id){
        conversionService.deleteConversionById(id);
        return ResponseEntity.noContent().build();
    }
}

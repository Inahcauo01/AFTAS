package com.example.aftas.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationException extends Exception{
    private CustomError customError;
}

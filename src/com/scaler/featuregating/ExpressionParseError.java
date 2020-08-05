package com.scaler.featuregating;

public class ExpressionParseError extends Exception{
    public ExpressionParseError(String message){
        super(message);
    }
}
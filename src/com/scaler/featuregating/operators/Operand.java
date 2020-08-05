package com.scaler.featuregating.operators;

public class Operand {
    public String type;
    public Object value;

    public Operand(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object typeValue() throws Exception{
        if (this.type.equals("String")){
            return (String) this.value;
        }
        else if (this.type.equals("Numeric")){
            return (Float) this.value;
        }
        else if (this.type.equals("Boolean")){
            return (Boolean) this.value;
        }
        else {
            throw new OperandTypeError("Operand type "+ this.type + " not implemented");
        }
    }
}

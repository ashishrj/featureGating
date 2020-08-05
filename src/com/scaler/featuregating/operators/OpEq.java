package com.scaler.featuregating.operators;

public class OpEq extends Operator {

    public OpEq(Float p) {
        this.Symbol = "==";
        this.NumOperators = 2;
        this.Precedence = p;
    }

    @Override
    public Boolean evaluate() throws Exception{
        if (this.consistentOperands()){
            return this.Operands.get(0).typeValue().equals(this.Operands.get(1).typeValue());
        }
        else{
            throw new OperandTypeError("Operand types not consistend");
        }
    }
}

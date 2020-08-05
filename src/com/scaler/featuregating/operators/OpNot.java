package com.scaler.featuregating.operators;

public class OpNot extends Operator{
    public OpNot(Float p) {
        this.Symbol = "!";
        this.NumOperators = 1;
        this.Precedence = p;
    }

    @Override
    public Boolean evaluate() throws Exception{
        if (this.consistentOperands()){
            if (this.Operands.get(0).type == "Boolean"){
                return !(boolean) this.Operands.get(0).value;
            }
            else{
                throw new OperandTypeError("Non Boolean type operand supplied to Not operator");
            }
        }
        else{
            throw new OperandTypeError("Operand types not consistend");
        }
    }
}

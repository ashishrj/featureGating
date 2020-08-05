package com.scaler.featuregating.operators;

public class OpOr extends Operator{
    public OpOr(Float p) {
        this.Symbol = "or";
        this.NumOperators = 2;
        this.Precedence = p;
    }

    @Override
    public Boolean evaluate() throws Exception{

        if (this.consistentOperands()){
            if (this.Operands.get(0).type == "Boolean"){
                return (boolean) this.Operands.get(0).value | (boolean) this.Operands.get(1).value;
            }else{
                throw new OperandTypeError("Boolean operands are not found");
            }
        }
        else{
            throw new OperandTypeError("Operand types not consistend");
        }
    }
}

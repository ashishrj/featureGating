package com.scaler.featuregating.operators;

public class OpLeq extends Operator {
    public OpLeq(Float p) {
        this.Symbol = "<=";
        this.NumOperators = 2;
        this.Precedence = p;
    }

    @Override
    public Boolean evaluate() throws Exception{
        if (this.consistentOperands()){
            int comp = ((Comparable) this.Operands.get(0).typeValue()).compareTo((Comparable) this.Operands.get(1).typeValue());
            if (comp <= 0) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            throw new OperandTypeError("Operand types not consistend");
        }
    }
}

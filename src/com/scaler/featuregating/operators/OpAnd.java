package com.scaler.featuregating.operators;

public class OpAnd extends Operator{

    public OpAnd(Float p) {
        this.Symbol = "and";
        this.NumOperators = 2;
        this.Precedence = p;
    }

    @Override
    public Boolean evaluate() throws Exception {
//        Boolean[] ops;
//        try {
//            ops = this.Operands.toArray(new Boolean[this.Operands.size()]);
//        }catch (ClassCastException e){
//            throw new OperandTypeError("Boolean operands are not found");
//        }
//        return ops[0] & ops[1];
        if (this.consistentOperands()){
            if (this.Operands.get(0).type == "Boolean"){
                return (boolean) this.Operands.get(0).value & (boolean) this.Operands.get(1).value;
            }else{
                throw new OperandTypeError("Boolean operands are not found");
            }
        }
        else{
            throw new OperandTypeError("Operand types not consistend");
        }
    }
}

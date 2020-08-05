package com.scaler.featuregating.operators;

import java.util.ArrayList;

public abstract class Operator implements Cloneable{
    public String Symbol;
    public Integer NumOperators;
    public Float Precedence;
    public ArrayList<Operand> Operands;

    public void addOperand(Operand Operand) {
        this.Operands.add(Operand);
    }

    public abstract Boolean evaluate() throws Exception;

    public Boolean consistentOperands(){
        // checks if all the operands of same type of not.
        String ref_type = Operands.get(0).type;
        for (int i=1;i<Operands.size();i++){
            if (Operands.get(i).type != ref_type){
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }
}

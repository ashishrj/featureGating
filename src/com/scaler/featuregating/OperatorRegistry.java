package com.scaler.featuregating;

import com.scaler.featuregating.operators.*;

import java.util.HashMap;

// OperatorRegistry, A singleton class
public class OperatorRegistry {
    public static String[] Slist = {">", "<", ">=", "<=", "==", "!", "and", "or"};
    public static Float[] Plist = {1f,1f,1f,1f,1f,2f,2f,2f};
    private static OperatorRegistry instance = null;

    public HashMap<String, Operator> operators = new HashMap<String, Operator>();

    private OperatorRegistry() {
//        for (String sym : this.Slist){
        for (int i=0;i<this.Slist.length;i++){
            String sym = this.Slist[i];
            float precedence = this.Plist[i];
            switch (sym){
                case ">":
                    operators.put(sym, new OpGt(precedence));
                    break;
                case "<":
                    operators.put(sym, new OpLt(precedence));
                    break;
                case ">=":
                    operators.put(sym, new OpGeq(precedence));
                    break;
                case "<=":
                    operators.put(sym, new OpLeq(precedence));
                    break;
                case "==":
                    operators.put(sym, new OpEq(precedence));
                    break;
                case "!":
                    operators.put(sym, new OpNot(precedence));
                    break;
                case "and":
                    operators.put(sym, new OpAnd(precedence));
                    break;
                case "or":
                    operators.put(sym, new OpOr(precedence));
                    break;
            }
        }
    }

    public static OperatorRegistry getInstance(){

        if(instance == null){
            instance = new OperatorRegistry();
        }

        return instance;
    }
}

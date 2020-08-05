package com.scaler.featuregating;


import com.scaler.featuregating.operators.Operand;
import com.scaler.featuregating.operators.OperandCountError;
import com.scaler.featuregating.operators.Operator;

import java.util.*;

public class Client {

    public static void main(String[] argv){

//        System.out.println("Hello, World");
//        postfix_parse("( age > 25 AND gender == \"Male\" )".split(" "));
//        postfix_parse("( age > 25 AND gender == \"Male\" ) OR ( past_order_amount > 10000 )".strip().split(" "));
//        postfix_parse("( age > 25 ) AND ( gender == \"Male\" OR past_order_amount > 10000 )".strip().split(" "));

        HashMap<String, Object> user = new HashMap<>();
        user.put("past_order_amount", 11000f);
        user.put("age", 24f);
        user.put("gender", "Female");

//        String[] user_keys = user.keySet().toArray(String[]::new);
//        for (String key: user_keys){
//            System.out.println("In user, value for key "+ key + " is of type "+user.get(key).getClass());
//        }

        String[] allExps = {
                "( age > 25 ) AND ( gender == \"Male\" OR past_order_amount > 10000 )",
                "( age > 25 AND gender == \"Male\" ) OR ( past_order_amount > 10000 )"
        };

        for (int i=0;i<allExps.length;i++) {
            String conditionalExpression = allExps[i];
            System.out.println("------------------------------------------------------------------");
            System.out.println("Conditional expression:");
            System.out.println(conditionalExpression);

            Boolean result = isAllowed(conditionalExpression, "", user);

            System.out.println("Evaluation of conditional: " + result.toString());
            System.out.println("------------------------------------------------------------------");
        }

    }

    public static Boolean isAllowed(String conditionalExpression, String featureName, HashMap<String, Object> user){
        // call to a parser to parse conditionalExpression
        // validity checks:
        //  1. required attribute is there in User class
        //  2. required operator is supported
        //  3. attribute compatibility with operator
        //  4. Syntax check for operator --> incomplete syntax
        // flexibility of syntax
        Boolean result = false;
        try {
            String[] tokens = conditionalExpression.strip().split(" ");
            ArrayList<String> postfix_tokens = postfix_parse(tokens);
            result = evaluate(postfix_tokens.toArray(new String[postfix_tokens.size()]), user);
        } catch (Exception e){
            e.printStackTrace();
        }
//        return true;
        return result;
    }

    public static boolean evaluate(String[] postfix_tokens, HashMap<String, Object> User) throws Exception{
        // evaluate as soon as get an operator symbol
        OperatorRegistry opReg = OperatorRegistry.getInstance();
        Stack<Operand> operandStack = new Stack<>();

        for (int i=0;i<postfix_tokens.length; i++){
            if (Arrays.asList(OperatorRegistry.Slist).contains(postfix_tokens[i])){
                // got an operator symbol
                Operator newop = (Operator) opReg.operators.get(postfix_tokens[i]).clone();

                ArrayList<Operand> operands = new ArrayList<>();

                try {
                    for (int j = 0; j < newop.NumOperators; j++) {
                        operands.add(operandStack.pop());
                    }
                } catch (EmptyStackException e){
//                    e.printStackTrace();
                    throw new OperandCountError();
                }
                Collections.reverse(operands);
                newop.Operands = operands;

                // in futute Operator.evaluate may return different types then this will need to change.
                boolean opResult = newop.evaluate();
//                System.out.println("for operator " + newop.Symbol + " result is " + opResult);
                operandStack.push(new Operand("Boolean", opResult));
            }
            else{
                String newToken = postfix_tokens[i];
                // logic to interpret a token into an object
                if (isString(newToken)){
                    operandStack.push(new Operand("String", newToken));
                }
                else if(isNumeric(newToken)){
                    operandStack.push(new Operand("Numeric", Float.parseFloat(newToken)));
                }
                else{
                    Object userAttr = User.get(newToken);
                    // definitely a poor choice to go with multiple try catch. What is the alternative?
                    try {
                        String str_attr = (String) userAttr;
                        operandStack.push(new Operand("String", str_attr));
                    }catch (Exception e1){
                        try{
                            Float float_attr = (Float) userAttr;
                            operandStack.push(new Operand("Numeric", float_attr));
                        }
                        catch (Exception e2){
                            throw new UserAttributeError("the type of user attribute not supported");
                        }
                    }

                }
            }
        }

        if (operandStack.size() != 1){
            throw new ExpressionParseError("Error at evaluate");
        }
        return (Boolean) operandStack.pop().value;

    }

    private static boolean isNumeric(String token) {
        if (token == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(token);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isString(String token) {
        if (token == null) {
            return false;
        }
        if (!token.startsWith("\"") | !token.endsWith("\"")){
            return false;
        }
        return true;
    }

    public static ArrayList<String> postfix_parse(String[] tokens){

        Stack<String> operator_stack = new Stack<String>();
        ArrayList<String> postfix_tokens = new ArrayList<>();


        OperatorRegistry opReg = OperatorRegistry.getInstance();

//        System.out.println("Printing All Tokens");
//        for (String atoken: tokens){
//            System.out.println(atoken);
//        }

        String tokenLower;
        String token;
        for (int i=0;i<tokens.length;i++){

            tokenLower = tokens[i].toLowerCase();
            token = tokens[i];
//            System.out.println(token);
            if (token.equals("(")){
                operator_stack.push(token);
            }
            else if(token.equals(")")){
//                System.out.println("found a closing bracket");
                while(!operator_stack.peek().equals("(")){
                    postfix_tokens.add(operator_stack.pop());
                }
                operator_stack.pop();
                // possibly an exception for closing bracket not found
            }
            else if (Arrays.asList(OperatorRegistry.Slist).contains(tokenLower)){
//                System.out.println("token: " + tokenLower);
                while (!operator_stack.isEmpty()){
//                    System.out.println("at top of operator stack is: "+ operator_stack.peek());
                    if (operator_stack.peek().equals("(")){
                        break;
                    }
                    if(opReg.operators.get(operator_stack.peek()).Precedence < opReg.operators.get(tokenLower).Precedence) {
                        postfix_tokens.add(operator_stack.pop());
                    }
                    else{
                        break;
                    }
                }
                operator_stack.push(tokenLower);
            }
            else{
                postfix_tokens.add(token);
            }

        }

        while(!operator_stack.isEmpty()){
            postfix_tokens.add(operator_stack.pop());
        }

//        System.out.println("Printing Postfix Tokens\n\n");
//        for (String atoken: postfix_tokens){
//            System.out.println(atoken);
//        }
        return postfix_tokens;
    }

}


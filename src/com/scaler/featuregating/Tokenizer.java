package com.scaler.featuregating;

import java.text.ParseException;
import java.util.ArrayList;

public class Tokenizer {
    public String text;

    public Tokenizer(String text) {
        this.text = text;
    }

//    public ArrayList<String> tokenize(){
////        String[] tokens = {};
//        ArrayList<String> tokens = new ArrayList<String>();
//        int i = 0;
//        while (i<this.text.length()){
//            System.out.println(this.text.charAt(i));
//            Character c = this.text.charAt(i);
//            if (c.equals(' ')) {
//                //
//            }
//            else if(c.equals('(')){
//                tokens.add("(");
//            }
//            else if(Character.isDigit(c)){
//                int j = i;
//                boolean isFloat = false;
//                while(Character.isDigit(this.text.charAt(j)) | c.equals('.')) {
//                    if (c.equals('.')){
//                        isFloat = true;
//                    }
//                    j++;
//                }
//                tokens.add(this.text.substring(i, j-1));
//            }
//            else if(true){
//                //
//            }
//            i++;
//        }
//        return tokens;
//    }
    public String[] tokenize(){
        return this.text.strip().split(" ");
    }


    public static void main(String[] argv){
        Tokenizer t = new Tokenizer("( age > 25 AND gender == \"Male\" ) OR ( past_order_amount > 10000 )");
        String[] tokens = t.tokenize();
        for (int i=0;i<tokens.length; i++) {
            System.out.println(tokens[i]);
        }
    }
}

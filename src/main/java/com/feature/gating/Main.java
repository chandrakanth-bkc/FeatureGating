package com.feature.gating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.feature.gating.Constants.*;

public class Main {
    public static void main(String args[]) {
        FeatureGate featureGate = new FeatureGate();

        // Test 1
        String condition1 = " (age <= 25.5 AND gender == \"Male\") OR (past_order_amount > 3000) OR attributes1 NONEOF attributes2 "; //evaluate to true
        String feature1 = "One Day Delivery";
        Map<String, Object> user1 = new HashMap<String, Object>();
        user1.put("age", 24);
        user1.put("gender", "Male");
        user1.put("past_order_amount", 100);
        user1.put("attributes1", new ArrayList<String>(Arrays.asList("bachelor", "professional")));
        user1.put("attributes2", new ArrayList<String>(Arrays.asList("senior_citizen", "homemaker", "retired")));

        if(featureGate.isAllowed(condition1, feature1, user1))
            System.out.println("User1 is eligible for the feature - "+ feature1);
        else
            System.out.println("User1 is not eligible for the feature - "+ feature1);

        //Test 2
        String condition2 = "(age BETWEEN 25 40) OR (cumulative_order_amount > 10000) AND (points1 ALLOF points2 AND (8 BETWEEN 3 5))"; //evaluate to false
        String feature2 = "Credit shopping";
        Map<String, Object> user2 = new HashMap<String, Object>();
        user2.put("age", 19);
        user2.put("cumulative_order_amount", 15673.5);
        user2.put("points1", new ArrayList<String>(Arrays.asList("foo", "bar")));
        user2.put("points2", new ArrayList<String>(Arrays.asList("bar", "foo")));

        if(featureGate.isAllowed(condition2, feature2, user2))
            System.out.println("User2 is eligible for the feature - "+ feature2);
        else
            System.out.println("User2 is not eligible for the feature - "+ feature2);

        //Add new operator ||
        featureGate.addNewOperator("NOR", new Operator(OR_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Boolean && o2 instanceof Boolean) {
                    Boolean x = (Boolean) o1;
                    Boolean y = (Boolean) o2;
                    return !(x || y);
                } else {
                    throw new Expression.ExpressionException("NOR operator cannot be used on the used data types");
                }
            }
        });
        String condition3 = "(age BETWEEN 25 40) NOR (cumulative_order_amount < 10000)"; //evaluate to true
        if(featureGate.isAllowed(condition3, feature2, user2))
            System.out.println("User2 is eligible for the feature - "+ feature2);
        else
            System.out.println("User2 is not eligible for the feature - "+ feature2);

    }
}


import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
public class CalculatorTDD
{
    /////////////////////////////////////
    ////// Testing isOperation
    @Test
    public void isOperationPassTest1()
    {
        assertTrue(Calculator.isOperation('+'));
    }
    @Test
    public void isOperationPassTest2()
    {
        assertTrue(Calculator.isOperation('-'));
    }
    @Test
    public void isOperationPassTest3()
    {
        assertTrue(Calculator.isOperation('*'));
    }
    @Test
    public void isOperationPassTest4()
    {
        assertTrue(Calculator.isOperation('/'));
    }
    @Test
    public void isOperationPassTest5()
    {
        assertTrue(Calculator.isOperation('^'));
    }

    @Test
    public void isOperationFalseTest1()
    {
        assertFalse(Calculator.isOperation(' '));
    }
    @Test
    public void isOperationFalseTest2()
    {
        assertFalse(Calculator.isOperation('!'));
    }
    @Test
    public void isOperationFalseTest3()
    {
        assertFalse(Calculator.isOperation('3'));
    }
    @Test
    public void isOperationFalseTest4()
    {
        assertFalse(Calculator.isOperation('@'));
    }
    @Test
    public void isOperationFalseTest5()
    {
        assertFalse(Calculator.isOperation('h'));
    }

    //////////////////////////////////////
    //////Testing validity
    @Test
    public void isValidPassRegularExpressionTest1()
    {
        assertTrue(Calculator.isValidExpression("2+2-3*4/5^2+22"));
    }
    @Test
    public void isValidPassDecimalsExpressionTest2()
    {
        assertTrue(Calculator.isValidExpression("15.03^2+45/3*4-.05"));
    }
    @Test
    public void isValidPassMultipleSpacesExpressionTest3()
    {
        assertTrue(Calculator.isValidExpression("         1  + 22333 -334  * 1 ^333   "));
    }
    @Test
    public void isValidFailOperationStartTest1()
    {
        assertFalse(Calculator.isValidExpression("+23-33"));
    }
    @Test
    public void isValidFailOperationEndTest2()
    {
        assertFalse(Calculator.isValidExpression("1+55-33^"));
    }
    @Test
    public void isValidFailNoOperationTest3()
    {
        assertFalse(Calculator.isValidExpression("35788"));
    }
    @Test
    public void isValidFailNonDigitTest4()
    {
        assertFalse(Calculator.isValidExpression("15-abc"));
    }
    @Test
    public void isValidFailConsecutiveOperationsTest5()
    {
        assertFalse(Calculator.isValidExpression("1+2--3"));
    }
    @Test
    public void isValidFailNoOperandsTest6()
    {
        assertFalse(Calculator.isValidExpression("++--"));
    }
    @Test
    public void isValidFailRandomSpecialCharacterTest7()
    {
        assertFalse(Calculator.isValidExpression("1+3-5^2-33+44$"));
    }

    //////////////////////////////////////////////
    //////Testing getOperands
    @Test
    public void checkNumberOfOperandsInSimpleExpressionTest1()
    {
        
        String sample = "3+5+7+9+0";
        ArrayList<Double> operandList = Calculator.getOperands(sample);
        assertEquals(operandList.size(),5);
    }

    @Test
    public void checkNumberOfOperandsInEmptyExpressionTest2()
    {
        
        String sample = "";
        ArrayList<Double> operandList = Calculator.getOperands(sample);
        assertEquals(operandList.size(),0);
    }

    @Test
    public void checkNumberOfOperandsWithMultipleExpressionTest3()
    {
        
        String sample = "3/5+7+9+0-3*5";
        ArrayList<Double> operandList = Calculator.getOperands(sample);
        assertEquals(operandList.size(),7);
    }

    @Test
    public void checkOperandsWithMultipleExpressionTest4()
    {
        
        String sample = "3/5+7+9+0-3*5";
        ArrayList<Double> operandList = Calculator.getOperands(sample);
        ArrayList<Double> actualList = new ArrayList<>();
        actualList.add(3.0);
        actualList.add(5.0);
        actualList.add(7.0);
        actualList.add(9.0);
        actualList.add(0.0);
        actualList.add(3.0);
        actualList.add(5.0);
        assertEquals(operandList,actualList);
    }


    /////////////////////////////////////////////////
    ///////////////////// Testing getOperations
    @Test
    public void checkNumberOfOperationsInSimpleExpressionTest1()
    {
        
        String sample = "3+5+7+9+0";
        ArrayList<Character> operandList = Calculator.getOperations(sample);
        assertEquals(operandList.size(),4);
    }

    @Test
    public void checkNumberOfOperationsInEmptyExpressionTest2()
    {
        
        String sample = "";
        ArrayList<Character> operandList = Calculator.getOperations(sample);
        assertEquals(operandList.size(),0);
    }

    @Test
    public void checkNumberOfOperationsWithMultipleExpressionTest3()
    {
        
        String sample = "3/5+7+9+0-3*5";
        ArrayList<Character> operandList = Calculator.getOperations(sample);
        assertEquals(operandList.size(),6);
    }

    @Test
    public void checkOperationsWithMultipleExpressionTest4()
    {
        
        String sample = "3/5+7+9+0-3*5";
        ArrayList<Character> operandList = Calculator.getOperations(sample);
        ArrayList<Character> actualList = new ArrayList<>();
        actualList.add('/');
        actualList.add('+');
        actualList.add('+');
        actualList.add('+');
        actualList.add('-');
        actualList.add('*');
        assertEquals(operandList,actualList);
    }

    ///////////////////////////////////////////
    ////////// Testing EvaluateExpression
    @Test
    public void evaluateExpressionAdditionTest1()
    {
        assertEquals(Calculator.evaluateExpression(.5,.2,'+'),.7);
    }
    @Test
    public void evaluateExpressionSubtractionTest1()
    {
        assertEquals(Calculator.evaluateExpression(6.0,8.0,'-'),-2.0);
    }
    @Test
    public void evaluateExpressionMultiplicationTest1()
    {
        assertEquals(Calculator.evaluateExpression(.5,9.0,'*'),4.5);
    }
    @Test
    public void evaluateExpressionDivisionTest1()
    {
        assertEquals(Calculator.evaluateExpression(3.0,4.0,'/'),.75);
    }
    @Test
    public void evaluateExpressionPowerTest1()
    {
        assertEquals(Calculator.evaluateExpression(3.0,4.0,'^'),81);
    }


    ////////////////////////////////////////////
    /////////// Testing getOperationPriority
    @Test
    public void getPriorityTest1()
    {
        assertEquals(Calculator.getOperationPriority('^'),3);
    }
    @Test
    public void getPriorityTest2()
    {
        assertEquals(Calculator.getOperationPriority('+'),1);
    }
    @Test
    public void getPriorityTest3()
    {
        assertEquals(Calculator.getOperationPriority('*'),2);
    }
    @Test
    public void getPriorityTest4()
    {
        assertEquals(Calculator.getOperationPriority('-'),1);
    }
    @Test
    public void getPriorityTest5()
    {
        assertEquals(Calculator.getOperationPriority('/'),2);
    }
    @Test
    public void getPriorityTest6()
    {
        assertEquals(Calculator.getOperationPriority('5'),-1);
    }

    /////////////////////////////////////////
    ////////// Testing findChain
    @Test
    public void findChainPriority1Test1()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+','-','+','+','-','^','*'));
        assertEquals(Calculator.findChain(0,operations,1),4);
    }
    @Test
    public void findChainPriority1Test2()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('*','^','-','-','-','/','*'));
        assertEquals(Calculator.findChain(2,operations,1),4);
    }
    @Test
    public void findChainPriority2Test3()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('*'));
        assertEquals(Calculator.findChain(0,operations,2),0);
    }
    @Test
    public void findChainPriority2Test4()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+','*','/','*','+','-','^','*'));
        assertEquals(Calculator.findChain(1,operations,2),3);
    }
    @Test
    public void findChainPriority3Test5()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+','-','-','*','+','^','^','^','^'));
        assertEquals(Calculator.findChain(5,operations,3),8);
    }
    @Test
    public void findChainPriority3Test6()
    {
        ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+','-','^','*','+','-','^','*'));
        assertEquals(Calculator.findChain(6,operations,3),6);
    }

    /////////////////////////////////////////
    //////////// Testing calculateChain
    @Test
    public void calculateChainSingleExponentTest1()
    {
        Calculator calc = new Calculator("2^4");
        assertEquals(calc.calculateChain(0,0,3),16.0);
    }
    @Test
    public void calculateChainMultipleExponentsTest2()
    {
        Calculator calc = new Calculator("1+2^2^2^1^5");
        assertEquals(calc.calculateChain(1,4,3),16.0);
    }
    @Test
    public void calculateChainSingleAddSubTest3()
    {
        Calculator calc = new Calculator("3+5");
        assertEquals(calc.calculateChain(0,0,1),8.0);
    }
    @Test
    public void calculateChainSingleAddSubTest4()
    {
        Calculator calc = new Calculator("3-5");
        assertEquals(calc.calculateChain(0,0,1),-2);
    }
    @Test
    public void calculateChainMultipleAddSubTest5()
    {
        Calculator calc = new Calculator("3+2-33+55+123-664");
        assertEquals(calc.calculateChain(0,4,1),-514.0);
    }
    @Test
    public void calculateChainMultipleAddSubTest6()
    {
        Calculator calc = new Calculator("3+2-33+55/5+123-664");
        assertEquals(calc.calculateChain(0,2,1),27);
    }
    @Test
    public void calculateChainSingleMulDivTest7()
    {
        Calculator calc = new Calculator("3*5");
        assertEquals(calc.calculateChain(0,0,2),15.0);
    }
    @Test
    public void calculateChainSingleMulDivTest8()
    {
        Calculator calc = new Calculator("10/5");
        assertEquals(calc.calculateChain(0,0,2),2.0);
    }
    @Test
    public void calculateChainMultipleMulDivTest9()
    {
        Calculator calc = new Calculator("2*5/3*89*0/5");
        assertEquals(calc.calculateChain(0,4,1),0.0);
    }

    //////////////////////////////////////////
    ////////////// Testing Solve Expression
    @Test
    public void solveExpressionTest1()
    {
        Calculator calc = new Calculator("15^2/5+344-257+489+2*1-55+3^4");
        calc.solveExpression(3);

        assertEquals(calc.getTotal(),649);
    }

    @Test
    public void solveExpressionTest2()
    {
        Calculator calc = new Calculator("25+43*85-543+23^2^1/4+234-159*3");
        calc.solveExpression(3);

        assertEquals(calc.getTotal(),3026.25);
    }

    @Test
    public void solveExpressionTest3()
    {
        Calculator calc = new Calculator("243/9*55-3+189-15^3/100/10*58+79");
        calc.solveExpression(3);

        assertEquals(calc.getTotal(),1554.25);
    }
}

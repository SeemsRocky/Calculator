import java.util.ArrayList;
import java.util.HashSet;

public class Calculator
{
    private static String originalExpression;
    private static ArrayList<Double> operands;
    private static ArrayList<Character> operations;
    private static double total;
    public Calculator(String expression)
    {
        originalExpression = expression;
        operands = getOperands(expression);
        operations = getOperations(expression);
    }

    /*
        Simple getter methods
     */
    public String getExpression() {return originalExpression;}
    public ArrayList<Double> getOperands()
    {
        return operands;
    }
    public ArrayList<Character> getOperations()
    {
        return operations;
    }
    public double getTotal(){ return total;}

    /*
        Returns if char is a valid operator character
     */
    public static boolean isOperation(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' ;
    }

    /*
        Checks if expression is valid and doesn't end with an operation
        Use regex to find only digits and operations and must begin and end with a number
     */
    public static boolean isValidExpression(String expression)
    {
        expression = expression.replace(" ", "");
        //need operator count to check that it's not just a number
        int operatorCount = 0;
        for(int i = 0; i < expression.length(); i++)
        {
            char c = expression.charAt(i);

            // check that the operator is surrounded by digits, and to make sure the next char after an operator is another operator
            if(isOperation(c))
            {
                // if operation at end or in beginning is false
                if(i==0 || i==expression.length()-1)
                {
                    return false;
                }
                char next = expression.charAt(i+1);
                char prev = expression.charAt(i-1);
                operatorCount++;
                // operator at start,end or repeats return false
                if(!((Character.isDigit(prev)||prev=='.') && (Character.isDigit(next) || next=='.')) )
                {
                    return false;
                }
            }
            // if anything in the expression is not an operator or a number, its false
            else if(!(Character.isDigit(c) || c == '.'))
            {
                return false;
            }
        }
        return operatorCount != 0 ? true:false;
    }

    /*
        Returns operands in the expression
     */
    public static ArrayList<Double> getOperands(String expression)
    {
        ArrayList<Double> operandArray = new ArrayList<>();
        int firstDigitIndex = 0;
        int lastDigitIndex = 0;
        while(lastDigitIndex<expression.length())
        {
            // find the operation character and obtain substring from first index counter up to position of char
            if(isOperation(expression.charAt(lastDigitIndex)))
            {
                String numberString = expression.substring(firstDigitIndex,lastDigitIndex);
                double number = Double.parseDouble(numberString);
                Double operand = number;
                operandArray.add(operand);
                firstDigitIndex = lastDigitIndex+1;
            }
            // if lastDigitIndex is about to go past length of string, just return end of substring.
            if(lastDigitIndex+1==expression.length())
            {
                String numberString = expression.substring(firstDigitIndex,expression.length());
                double number = Double.parseDouble(numberString);
                Double operand = number;
                operandArray.add(operand);
                firstDigitIndex = lastDigitIndex;

                break; //remember to exit after adding last number
            }
            else
            {
                //if lastDigitIndex+1!= expression.length then we can continue on in the string.
                lastDigitIndex++;
            }


        }
        return operandArray;
    }

    /*
        Returns operations in expression
     */
    public static ArrayList<Character> getOperations(String expression)
    {
        ArrayList<Character> operationArray = new ArrayList<>();

        for(int index =0;index<expression.length();index++)
        {
            if(isOperation(expression.charAt(index)))
            {
                Character operation = expression.charAt(index);
                operationArray.add(operation);
            }

        }
        return operationArray;
    }

    /*
        Do basic math
     */
    public static double evaluateExpression(Double operand1, Double operand2,char operation)
    {
        switch(operation)
        {
            case '^':
                return Math.pow(operand1,operand2);
            case '+':
                return operand1+operand2;
            case '-':
                return operand1-operand2;
            case '*':
                return operand1*operand2;
            case '/':
                if(operand2==0)
                {
                    throw new ArithmeticException("Why are you trying to divide by zero");
                }
                return operand1/operand2;
            default:
                return 0.0; // figure out what to make the bad value would be
        }

    }

    /*
        Returns priority according to order of operations and -1 if not operation
     */
    public static int getOperationPriority(char c)
    {
        switch(c)
        {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return -1;
        }
    }

    /*
        Removes the operations and operands that have been already used for according to orders of operation
     */
    public static void removeFromArrayList(int start,int end,ArrayList<Double> operands,ArrayList<Character> operations)
    {
        operands.remove(end+1);
        for(int i=end;i>=start;i--)
        {
            operands.remove(i);
            operations.remove(i);
        }
    }

    /*
        Returns last index of the operation chain of the operation according to the priority given
     */
    public static int findChain(int startPos,ArrayList<Character> operations, int priority)
    {
        int pos = startPos;
        if(priority == 3)
        {
            while(pos<operations.size())
            {
                if(operations.get(pos).charValue()!='^')
                {
                    break;
                }
                pos++;
            }
            return pos-1;
        }
        if(priority == 2)
        {
            while(pos<operations.size())
            {
                if(operations.get(pos).charValue()=='*' || operations.get(pos).charValue()=='/')
                {
                    pos++;
                }
                else
                {
                    break;
                }
            }
            return pos-1;
        }
        if(priority == 1)
        {
            while(pos<operations.size())
            {
                if(operations.get(pos).charValue()=='+' || operations.get(pos).charValue()=='-')
                {
                    pos++;
                }
                else
                {
                    break;
                }
            }
            return pos-1;
        }
        return -1;
    }

    /*
        Calculates part of the expression where exponents are chained
     */
    public double calculateChain(int startIndex, int endIndex, int priority)
    {
        if(priority==3)
        {
            int lastIndex = endIndex;
            double base = operands.get(lastIndex);
            double exp = operands.get(lastIndex+1);
            double result = Math.pow(base,exp);

            lastIndex--;
            startIndex++;
            while(startIndex<=endIndex)
            {
                exp = result;
                base = operands.get(lastIndex);
                result = Math.pow(base,exp);
                lastIndex--;
                startIndex++;

            }
            return result;
        }
        else
        {
            int operandCounter = startIndex;
            int operationCounter = startIndex;
            double operand1 = operands.get(operandCounter);
            double operand2 = operands.get(operandCounter+1);
            char operation = operations.get(operationCounter);
            double result = evaluateExpression(operand1,operand2,operation);

            if(operationCounter==endIndex)
            {
                return result;
            }
            else
            {
                operationCounter++;
            }
            for(int i=operationCounter;i<=endIndex;i++)
            {

                operand1 = result;
                if (operandCounter+2<=operands.size()-1)
                {
                    operand2 = operands.get(operandCounter+2);
                    operandCounter++;
                }
                else
                {
                    break;
                }

                result = evaluateExpression(operand1,operand2,operations.get(i));

            }
            return result;
        }

    }
    /*
        Returns which index to use for given priority
     */
    public static int findNextIndex(ArrayList<Character> operations,int priority)
    {
        if(priority == 3)
        {
            int exponentIndex = operations.indexOf('^');
            return exponentIndex;
        }
        else if(priority == 2)
        {
            int multiplicationIndex = operations.indexOf('*');
            int divisionIndex = operations.indexOf('/');
            if(multiplicationIndex == -1 && divisionIndex == -1)
            {
                return -1;
            }
            else if(multiplicationIndex == -1 && divisionIndex > -1)
            {
                return divisionIndex;
            }
            else if(divisionIndex == -1 && multiplicationIndex > -1)
            {
                return multiplicationIndex;
            }
            else
            {
                return multiplicationIndex>divisionIndex ? divisionIndex : multiplicationIndex;
            }
        }
        else
        {
            int additionIndex = operations.indexOf('+');
            int subtractionIndex = operations.indexOf('-');
            if(additionIndex == -1 && subtractionIndex == -1)
            {
                return -1;
            }
            else if(additionIndex == -1 && subtractionIndex > -1)
            {
                return subtractionIndex;
            }
            else if(subtractionIndex == -1 && additionIndex > -1)
            {
                return additionIndex;
            }
            else
            {
                return additionIndex>subtractionIndex ? subtractionIndex : additionIndex;
            }
        }
    }


    /*
        Calculates the total and stores it in the private variable after according to operation
     */
    public void solveExpression(int operationPriority)
    {

        if(operationPriority==0)
        {
            total = operands.get(0);
        }
        else if(operationPriority==3)
        {

            int currentIndex = findNextIndex(operations,3);

            if(currentIndex ==-1)
            {
                solveExpression(2);
            }
            else
            {
                while(currentIndex <= operations.size() && currentIndex!=-1)
                {
                    int endOfChain = findChain(currentIndex,operations,3);

                    double result = this.calculateChain(currentIndex,endOfChain,3);
                    removeFromArrayList(currentIndex,endOfChain,operands,operations);

                    operands.add(currentIndex,result);

                    currentIndex = findNextIndex(operations,3);

                }
                solveExpression(2);
            }

        }
        else if(operationPriority==2)
        {
            int currentIndex = findNextIndex(operations,2);

            if(currentIndex == -1)
            {
                solveExpression(1);
            }
            else
            {
                while(currentIndex <= operations.size() && currentIndex > -1)
                {
                    int endOfChain = findChain(currentIndex,operations,2);
                    double result = this.calculateChain(currentIndex,endOfChain,2);
                    removeFromArrayList(currentIndex,endOfChain,operands,operations);
                    operands.add(currentIndex,result);

                    currentIndex = findNextIndex(operations,2);
                }
                solveExpression(1);
            }

        }
        else
        {
            int currentIndex = findNextIndex(operations,1);

            if(currentIndex == -1)
            {
                solveExpression(0);
            }
            else
            {
                while(currentIndex <= operations.size() && currentIndex > -1)
                {
                    int endOfChain = findChain(currentIndex,operations,1);
                    double result = this.calculateChain(currentIndex,endOfChain,2);

                    removeFromArrayList(currentIndex,endOfChain,operands,operations);
                    operands.add(currentIndex,result);

                    currentIndex = findNextIndex(operations,1);
                }
                solveExpression(0);
            }

        }
    }

}

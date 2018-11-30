import java.util.Scanner;

public class CalculatorDriver {
    public static void  main (String[] args)
    {

        System.out.println("Enter an expression to proceed or type quit to exit.");
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext())
        {
            //System.out.println("Enter an expression to proceed or type quit to exit.");
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("quit"))
            {
                System.out.println("Calculator is turning off");
                break;
            }
            else
            {

                boolean isValid = Calculator.isValidExpression(input);
                if(isValid)
                {
                    Calculator calc = new Calculator(input);
                    calc.solveExpression(3);
                    System.out.println("The answer is: "+ calc.getTotal());

                }
                else
                {
                    System.out.println("That is not a valid expression! \nPlease enter a valid expression or type quit to exit.");
                }
            }
        }
    }
}

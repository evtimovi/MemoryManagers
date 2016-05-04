public class Foo
{
    private static int logBase2(int x)
    {
        if(x == 1)
        {
            return 0;
        }
        else
        {
            return 1 + logBase2((int) x/2);
        }
    }

    public static void main(String[] args)
    {
        int number = Integer.parseInt(args[0]);
        System.out.println("The log base 2 of " + args[0] + " is " + logBase2(number));
    }
}

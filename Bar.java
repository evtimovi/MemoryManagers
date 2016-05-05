public class Bar
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


    /**
     * method used to translate address into VIRTUAL page number, thereby stripping the offset.
     * will be useful as helper in read and write, but also in the algorithms that need future knowledge of page accesses
     */
    public static int whichPage(int address, int sizeOfPage)
    {
        Controller.PAGE_SIZE = sizeOfPage;
        Controller.OFFSET_LENGTH = logBase2(Controller.PAGE_SIZE);
        Controller.PAGE_NUMBER_LENGTH = Controller.VIRTUAL_ADDRESS_LENGTH - Controller.OFFSET_LENGTH;
        Controller.FRAME_NUMBER_LENGTH = Controller.PHYSICAL_ADDRESS_LENGTH - Controller.OFFSET_LENGTH;
        Controller.NUM_OF_PAGES = (int) Math.pow((double) 2, (double) Controller.PAGE_NUMBER_LENGTH);
        Controller.NUM_OF_FRAMES = (int) Math.pow((double) 2, (double) Controller.FRAME_NUMBER_LENGTH);

        //convert to bit string
        String bitAddress = Integer.toBinaryString(address); 

        System.out.println("bit address: " + bitAddress);

        bitAddress = padWithZeroes(bitAddress);
        System.out.println("bit address after format: " + bitAddress);

        //get just the front - i.e. the page number
        String pageNumBinary = bitAddress.substring(0, Controller.PAGE_NUMBER_LENGTH);

        System.out.println("page num in binary: " + pageNumBinary);

        //parse that to a real integer and return it
        return Integer.parseInt(pageNumBinary, 2);
    }
  
    public static String padWithZeroes(String ba)
    {
       int howMany =Controller.VIRTUAL_ADDRESS_LENGTH - ba.length();
       String pad = "";
       for(int i = 0; i < howMany; i++)
       {
           pad += "0";
       }

       return pad.concat(ba);
    }
    public static void main(String[] args)
    {
        int number = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        System.out.println("address " + args[0] + " translates to page number " + whichPage(number, size));
    }
}

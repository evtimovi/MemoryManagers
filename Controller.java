/**
 * main entry point for the project.
 * CS 406 Operating Systems
 * Spring 2016, Lafayette College
 * Professor Amir Sadovnik
 *
 * Project 3
 *
 * Renan Dincer and Ivan Evtimov
 *
 * This class is the main entry point for the project, its main method should be invoked from the command line to kick off the simulation.
 *
 * @author Renan Dincer
 * @author Ivan Evtimov
 *
 */

import java.util.*;
import java.io.*;

public class Controller
{

    //known constants - e.g. lenght of the virtual address (in bits)
    public static final int VIRTUAL_ADDRESS_LENGTH = 16;
    public static final int PHYSICAL_ADDRESS_LENGTH = 11;
    public static final int NUM_PHYSICAL_ADDRESSES = 2048;
    public static final int NUM_VIRTUAL_ADDRESSES = 65536;

    /** 
     * all of these should only be set up in the setup method here, 
     * access is simplified by making them public, but they should not be modified.
     */
    //constants to be determined by user input - how many bits of each address are dedicated to these
    public static int OFFSET_LENGTH;
    public static int PAGE_NUMBER_LENGTH;
    public static int FRAME_NUMBER_LENGTH;

    //non-bit constants, e.g. size of page = number of bytes in a page
    public static int PAGE_SIZE;
    public static int NUM_OF_PAGES;
    public static int NUM_OF_FRAMES;

    /**
     * all the input file will be scanned in first and stored in this object, 
     * broken up for string accesses
     */
    private static ArrayList<String> future;

    /**
     * the memory manager - this will be initialized to different subclasses depending on the requested algo
     * The memory manager also needs to keep track of the statistics and the output that will be shown to the user.
     */
    private static MemoryManager mm;

    /**
     * holds all processes in the system, will be initialized during setup time
     */
    private static HashMap<Integer, Process> procMap;

    /**
     * the following method will read the input file and fill up the futureAccessStrings array
     * TO-DO: appropriate parameters as filled up from the arguments supplied by the user
     * @param file the fileName of the file to be read
     * @throws IOException upon problems with the scanner, should be handled in main
     */
    public static void readFile(String file) throws IOException
    {
        future = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader (file) );
        while (bufferedReader.ready())
        {
            future.add(bufferedReader.readLine());
        }
        bufferedReader.close();
    }

    /**
     * next memory access - to be called until the futureAccessStrings array is empty (no more memory references)
     * 1. get the next thing from the futureAccessStrings array (or queue or whatever it is) - already happened in main
     * 2. look for a process with that PID; if not found - create and initialize a new process with "default" page table 
     * - frames that are invalid (e.g. not loaded into memory)
     * 3. proceed to call read or write on that process 
     */
    private static void next(int pid, int address, boolean read)
    {
        Process proc = procMap.get(new Integer(pid));

        if(proc == null)
        {
            proc = new Process();
            procMap.put(new Integer(pid), proc);
        }

        if(read)
        {
            proc.read(address);
        }
        else
        {
            proc.write(address);
        }
    }

    /**
     * performs set up based on the input parameters:
     * 0. calculate number of bits for page number and for offset and set appropriate global variables,
     *    calculate how many frames and how many pages we have, update global variables and pass to MemoryManager
     * 1. initialize MemoryManager and memory abstraction objects (such as PhysicalMemory)
     * --- this happens in the constructor of the Memory object
     * initialization and setup of Process objects will NOT happen here, but on the fly - see "next"
     * @param sizeOfPage will correspond to user argument and will be used for the calculations in step 0
     * @param algoChoice the choice of algorithm for the user
     */
    private static void setup(int sizeOfPage, String algoChoice)
    {
        PAGE_SIZE = sizeOfPage;
        OFFSET_LENGTH = logBase2(PAGE_SIZE);
        PAGE_NUMBER_LENGTH = VIRTUAL_ADDRESS_LENGTH - OFFSET_LENGTH;
        FRAME_NUMBER_LENGTH = PHYSICAL_ADDRESS_LENGTH - OFFSET_LENGTH;
        NUM_OF_PAGES = (int) Math.pow((double) 2, (double) PAGE_NUMBER_LENGTH);
        NUM_OF_FRAMES = (int) Math.pow((double) 2, (double) FRAME_NUMBER_LENGTH);

        switch(algoChoice)
        {
            case "optimal":
                mm = new OptimalMemoryManager();
                break;
            case "fifo":
                mm = new FIFOMemoryManager();
                break;
            case "lru":
                mm = new LRUMemoryManager();
                break;
            case "secondChance":
                mm = new SecondChanceMemoryManager();
                break;
            case "enhancedSecondChance":
                mm = new EnhancedSecondChanceMemoryManager();
                break;
            default:
                mm = new CustomMemoryManager;
        }

        procMap = new HashMap<Integer, Process>();
    }

    /**
     * method to return the log base 2 of an item x, with the decimal part cut off (rounded down)
     */
    private static int logBase2(int x)
    {
        if(x = 1)
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
        int pid, address;
        boolean read;

        String algoType = args[0];
        int pageSize = Integer.parseInt(args[1]);
        String file = args[2];

        //step 1: reading the file
        try
        {
            readFile(file);
        }
        catch (IOException e)
        {
            System.out.println ("IO Exception occured when reading the input file. Exiting...");
            System.out.println(e);
            System.exit(1);
        }

        //step 2: setup - initializing page size, etc
        setup(pageSize, algoType);

        //step 3: actual execution loop - parse String and call next
        for (int i = 0; i < future.size(); i++)
        {
            String ma = future.get(i);

            try
            {
                Scanner sc = new Scanner(ma).useDelimiter(",");
                pid = sc.nextInt();
                address = sc.nextInt();
                read = sc.next().equals("R");
                sc.close();
            }
            catch(IOException e)
            {
                System.out.println ("IO Exception occured when parsing lines from the input file. Exiting...");
                System.out.println(e);
                System.exit(1);
            }

            next(pid, address, read);
        }
    }
}

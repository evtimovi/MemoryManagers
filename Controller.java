/**
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

    /**
     * all the input file will be scanned in first and stored in this object, 
     * broken up for string accesses
     */
    private ArrayList<String> futureAccessStrings;

    /**
     * the memory manager - this will be initialized to different subclasses depending on the requested algo
     * The memory manager also needs to keep track of the statistics and the output that will be shown to the user.
     */
    private MemoryManager mm;

    /**
     * holds all processes in the system, will be initialized during setup time
     */
    private ArrayList<String> procArr;

    /**
     * the following method will read the input file and fill up the futureAccessStrings array
     * TO-DO: appropriate parameters as filled up from the arguments supplied by the user
     * @throws IOException upon problems with the scanner, should be handled in main
     */
    public void readInputFile() throws IOException
    {

    }

    /**
     * next memory access - to be called until the futureAccessStrings array is empty (no more memory references)
     * 1. get the next thing from the futureAccessStrings array (or queue or whatever it is)
     * 2. look for a process with that PID; if not found - create and initialize a new process with "default" page table 
     * - frames that are invalid (e.g. not loaded into memory)
     * 3. proceed to call read or write on that process 
     */
    public void next()
    {

    }

    /**
     * performs set up based on the input parameters:
     * 0. calculate number of bits for page number and for offset and set appropriate global variables,
     *    calculate how many frames and how many pages we have, update global variables and pass to MemoryManager
     * 1. initialize MemoryManager and memory abstraction objects (such as PhysicalMemory)
     * initialization and setup of Process objects will NOT happen here, but on the fly - see "next"
     * @param sizeOfPage will correspond to user argument and will be used for the calculations in step 0
     */
    private void setup(int sizeOfPage)
    {

    }
    public static void main(String[] args)
    {
    }
}

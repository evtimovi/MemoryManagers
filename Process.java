/**
 * represents a process and thus holds a page table and allows the Controller to tell it to read or write from a given virtual address.
 *
 * CS 406 Operating Systems
 * Spring 2016, Lafayette College
 * Professor Amir Sadovnik
 *
 * Project 3
 *
 * Renan Dincer and Ivan Evtimov
 *
 * @author Renan Dincer
 * @author Ivan Evtimov
 *
 */

import java.util.*;

public class Process
{
    /**
     * this might need a more sophisticated implementation than an ArrayList, but
     * it represents the page table and is addressed by frame number
     * this is essentially the Virtual Address Space of the process so it should always be initialized as follows:
     * - exactly as big as the total possible number of pages in the system (need to reference global user-supplied parameters during initialization)
     * - at the point of initialization, each Frame object being held in here has its number set to -1 and is assumed to not be loaded in main memory
     * as frames are accessed and loaded into main memory, each Frame object is updated accordingly
     */
    private ArrayList<Frame> pageTable;

    /** this process' unique identifier*/
    private int pid; 

    /**
     * default constructs an empty process based on the Controller parameters.
     * note that for this constructor to execute properly, these parameters need to have intialized by a call to setup in the Controller
     * NO PID ASSIGNED!!!!
     */
    public Process() throws ParametersUninitializedException
    {
        if(! Controller.PARAM_INIT)
        {
            throw new ParametersUninitializedException("");
        }

        pageTable = new ArrayList<Frame>(Controller.NUM_OF_PAGES);

        for (Frame f : pageTable)
        {
            f = new Frame();
        }
    }

    /** same as default, but pid is assigned as provided, use this not the default */
    public Process(int id) throws ParametersUninitializedException
    {
        if(! Controller.PARAM_INIT)
        {
            throw new ParametersUninitializedException("");
        }

        pid = id;

        pageTable = new ArrayList<Frame>(Controller.NUM_OF_PAGES);
        
        for (int i = 0; i < Controller.NUM_OF_PAGES; i++)
        {
            pageTable.add(new Frame(pid));
        }
    }

    /**
     * method to simulate a read.
     * will follow this procedural flow:
     * 1. strip the offset from the virtual address being passed as a parameter 
     *  (need to reference global parameters about the bits allocated to the offset here)
     * 2. use resulting page number to look up a Frame object in its page table
     * 3. get the Frame object's number and call the MemoryManager in order to access that frame*
     * * note that we need to figure out who owns the memory manager here - is it passed as a parameter or is a reference to it stored in each process object?
     * also note that this Frame object being used here should be, if all is implemented as intended, 
     * the EXACT SAME instance that is also stored in the PhysicalMemory object
     * of course, this is a departure from reality, but makes this simulation more efficient 
     * because we don't have to go back to the process to update its page table when we do page replacement
     *
     * @return translated address (that is, full physical address)
     */
    public void read(int address)
    {
        int pageNum = whichPage(address);
        Frame fr = pageTable.get(pageNum);
        MemoryManager mm = Controller.getMemoryManager();
        int replacedFrame = mm.reference(fr);
     
        if(replacedFrame < 0) //no page fault
        {
            printNoPageFaultMsg(fr.getNumber());
        	System.out.println("\tVirtual address: " + address + "-> Physical address: " + this.whichPhysicalAddress(address, fr.getNumber()) + " ");
        }
        else
        {
            printLoadedMsg(pageNum, this.pid, replacedFrame);
        	System.out.println("\tVirtual address: " + address + "-> Physical address: " + this.whichPhysicalAddress(address, replacedFrame) + " ");
        }

        
    }
    

    /**
     * method to simulate a read.
     * follow steps for read BUT
     * add step 4:
     * 4. set dirty bit to true
     */
    public void write(int address)
    {
        int pageNum = whichPage(address);
        Frame fr = pageTable.get(pageNum);
        MemoryManager mm = Controller.getMemoryManager();
        int replacedFrame = mm.reference(fr);
        fr.setDirtyBit();

        if(replacedFrame < 0) //no page fault
        {
            printNoPageFaultMsg(fr.getNumber());
        	System.out.println("\tVirtual address: " + address + "-> Physical address: " + this.whichPhysicalAddress(address, fr.getNumber()) + " ");
        }
        else
        {
            printLoadedMsg(pageNum, this.pid, replacedFrame);
       		System.out.println("\tVirtual address: " + address + "-> Physical address: " + this.whichPhysicalAddress(address, replacedFrame) + " ");
        }
    }

    /**
     * method used to translate address into VIRTUAL page number, thereby stripping the offset.
     * will be useful as helper in read and write, but also in the algorithms that need future knowledge of page accesses
     */
    public int whichPage(int address)
    {
        //convert to bit string and pad with leading 0s to make it 16 bits long
        String bitAddress = Integer.toBinaryString(address); 
        bitAddress = padWithZeroes(bitAddress);


        //get just the front - i.e. the page number
        String pageNumBinary = bitAddress.substring(0, Controller.PAGE_NUMBER_LENGTH);

        //parse that to a real integer and return it
        return Integer.parseInt(pageNumBinary, 2);
    }

    public int whichPhysicalAddress(int address, int frameNum)
    {
        //convert to bit string and pad with leading 0s to make it 16 bits long
        String bitAddress = Integer.toBinaryString(address); 
        bitAddress = padWithZeroes(bitAddress);

		String frameBinary = Integer.toBinaryString(frameNum);

        //get just the back - i.e. the offset
        String offsetBinary = bitAddress.substring(Controller.PAGE_NUMBER_LENGTH);
		String physicalAddressBinary = frameBinary + offsetBinary;

        //parse that to a real integer and return it
        return Integer.parseInt(physicalAddressBinary, 2);
    }

    /** helper utility to pad a string with zeroes up to the virtual address size length */
    public static String padWithZeroes(String ba)
    {
       int howMany = Controller.VIRTUAL_ADDRESS_LENGTH - ba.length();
       String pad = "";
       for(int i = 0; i < howMany; i++)
       {
           pad += "0";
       }

       return pad.concat(ba);
    }

    /** accessors for the PID */
    public int getPid(){return pid;}
    public int getPID(){return pid;}


    /** prints the message describing the result of the operation */
    public void printLoadedMsg(int page, int pid, int frame)
    {
        String out = "";

        out += "loaded page #";
        out += page;
        out += " of process #";
        out+= pid;
        out += " to frame #";
        out += frame;
//       out += " with ";
//       out += (frame ? "" : " no ");
//       out += "replacement.";

        System.out.println(out);
    }

    public void printNoPageFaultMsg(int frame)
    {
        System.out.println("no page fault. accessed frame #" + frame);
    }
}

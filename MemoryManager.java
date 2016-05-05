/**
 * abstract parent of all memory managers with functionality common to all of them.
 *
 * CS 406 Operating Systems
 * Spring 2016, Lafayette College
 * Professor Amir Sadovnik
 *
 * Project 3
 *
 * Renan Dincer and Ivan Evtimov
 *
 * This class is the abstract parent of all memory managers (different based on algorithms) - it provides commmon functionality such as statistics and calls to PhysicalMemory to perform the actual page repalcement.
 *
 * @author Renan Dincer
 * @author Ivan Evtimov
 *
 */

import java.util.*;

public abstract class MemoryManager
{

    /**
     * object that holds frames that are actually loaded - used to keep track of free space and frame allocation
     */
    protected PhysicalMemory physMem;

    /**
     * statistics counting variables, eg number of memory accesses, etc
     */
    protected int memAccessCount;
    protected int pageFaultCount;

    /**
     * constructor to do common setup - SHOULD BE CALLED BY ALL CHILDREN as super!!!
     */
    public MemoryManager() throws ParametersUninitializedException
    {
        if(!Controller.PARAM_INIT)
        {
            throw new ParametersUninitializedException("");
        }

        physMem = new PhysicalMemory();
        memAccessCount = 0;
        pageFaultCount = 0;
    }


   
    /**
     * method to abstract frame access and reference.
     * note that the Process object calling this method should have taken care (or will take care) of updating the dirty bit
     * also note that the offset does not really matter since this is just a simulation, we will be passing only the relevant frame number
     * this method will perform the following:
     * 0. update statistics for memory access count
     * 1. check  the validity of the frame (is it in main memory?); if yes - we are done, if not, continue below
     * 2. update statistics for page fault count
     * 3. use a page replacement algorithm to decide what frame gets kicked out or, (subclass responsibility)
     * if we have a free frame, what is the free frame we are putting the page in
     * 4. call replacementHandler - subclass responsibility
     *
     * IMPORTANT: the actual frame needs to be passed in by the process - 
     * if this is a new frame, this parameter is going to be what is actually put into the PhysicalMemory table
     *
     * @return replaced frame number or -1 if no page fault occurred
     */
    public int reference(Frame incomingFrame)
    {
        memAccessCount++; //statistics-keeping
        
        int victimFrameNum = -1;

        // if it is valid, we are done, no disc access
        if(!incomingFrame.isValid())
        {
            pageFaultCount++;//statistics-keeping

            int freeFrameNum = physMem.getFreeFrame();

            if(freeFrameNum > -1)
            {
                victimFrameNum = freeFrameNum;           
            }
            else
            {
                victimFrameNum = this.chooseVictim().getNumber();
            }
            
            //the replacement handler is left to the children to implement - they might need to 
            //keep special track of who is being accessed, etc
            replacementHandler(victimFrameNum, incomingFrame);

        }

        return victimFrameNum; //if a page was loaded, this would be set to something other than -1
    }

    /**
     * method to perform replacement and update any algorithm-specific statistics - left to child classes.
     * this will be algorithm-specific, so it is left to the inheriting classes.
     *
     * In general, we expect this handler to do the following:
     * 1. update the soon-to-be-kicked-out Frame object to set its number to -1, clear its dirty bit and do other algo-specific resets, increment memory write access if the frame is dirty
     * 2. update the frame that is coming in to set its number to the frame it will be allocated in memory
     * 3. make the appropriate call to the PhysicalMemory object to do the actual replacement. physsmam.repleace with (xy)
     *
     * @param incomingFrame the frame that will be brought in from the backing store
     */
    protected abstract void replacementHandler(int victimFrameNum, Frame incomingFrame);

    protected abstract Frame chooseVictim();
}

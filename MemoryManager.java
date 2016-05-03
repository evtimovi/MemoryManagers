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
    private PhysicalMemory physMem;

    /**
     * TO-DO: add appropriate statistics counting variables, eg number of memory accesses, etc
     */
   
    /**
     * method to abstract frame access and reference.
     * note that the Process object calling this method should have taken care (or will take care) of updating the dirty bit
     * also note that the offset does not really matter since this is just a simulation, we will be passing only the relevant frame number
     * this method will perform the following:
     * 1. check  the validity of the frame (is it in main memory?); if not, invoke page replacement handler (private method below)
     * 2. update statistics
     *
     * IMPORTANT: the actual frame needs to be passed in by the process - 
     * if this is a new frame, this parameter is going to be what is actually put into the PhysicalMemory table
     */
    public void reference(Frame incomingFrame)
    {

    }

    /**
     * method to find appropriate page to be replaced, replace it and update the necessary data structures.
     * this will be algorithm-specific, so it is left to the inheriting classes.
     * Note: it might be useful to wrap the data structure updates in another method 
     * that is implemented here in the parent because that will be common across most algorithms
     *
     * In general, we expect this handler to do the following:
     * 1. use a page replacement algorithm to decide what frame gets kicked out or,
     * if that is not necessary, what is the free frame we are putting the page in
     * 2. update the soon-to-be-kicked-out Frame object to set its number to -1, clear its dirty bit and do other algo-specific resets
     * 3. update the frame that is coming in to set its number to the frame it will be allocated in memory
     * 4. make the appropriate call to the PhysicalMemory object to do the actual replacement
     * DO NOT dereference from the Process page table, actually do not even care about the process page table
     *
     * @param incomingFrame the frame that will be brought in from the backing store
     */
    private abstract void replacementHandler(Frame incomingFrame)
    {

    }
}

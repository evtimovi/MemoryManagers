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
     */
    public void reference(int frameNumber)
    {

    }

    /**
     * method to find appropriate page to be replaced, replace it and update the necessary data structures.
     * this will be algorithm-specific, so it is left to the inheriting classes.
     * Note: it might be useful to wrap the data structure updates in another method 
     * that is implemented here in the parent because that will be common across most algorithms
     */
    private abstract void replacementHandler()
    {

    }
}

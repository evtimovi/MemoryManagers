/**
 * represents the main memory of the system by holding an array of frames.
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

public class PhysicalMemory
{
   /**
    * TO-DO: some sort of data structure to hold the frame object
    * this data structure needs to be initialized to size of the maximum possible number of frames in the system
    * (needs to reference the global parameters specified by the user)
    */

    /**
     * also consider: abstractions about free space, etc
     */

    /**
     * just returns a frame number that is free and can be written to - designed to be called from a MemoryManager object
     * @return the frame number of the free frame or -1 if none
     */
    public int getFreeFrame()
    {
        return -1;
    }


    /**
     * method to perform the replacement, dereferences the specified frame and puts in the other one
     * 
     */
    public void replaceWith(int victim, Frame incoming)
    {

    }
}

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

import java.util.*;

public class PhysicalMemory
{
   /**
    * this data structure needs to be initialized to size of the maximum possible number of frames in the system
    * (needs to reference the global parameters specified by the user)
    */
    private ArrayList<Frame> pm;

    /**
     * also consider: abstractions about free space, etc
     */

    public PhysicalMemory() throws ParametersUninitializedException
    {
        if( ! Controller.PARAM_INIT )
        {
            throw new ParametersUninitializedException("");
        }

        pm = new ArrayList<Frame>(Controller.NUM_OF_FRAMES);

        for(int i = 0; i < Controller.NUM_OF_FRAMES; i++)
        {
            pm.add(null);
        }
    }

    /**
     * just returns a frame number that is free and can be written to - designed to be called from a MemoryManager object
     * @return the frame number of the free frame or -1 if none
     */
    public int getFreeFrame()
    {
        for(int i = 0; i < pm.size(); i++)
        {
            if(pm.get(i) == null)
            {
                return i;
            }
        }
        return -1;
    }


    /**
     * method to perform the replacement, dereferences the specified frame and puts in the other one
     * this method doesn't care if the victim position is empty or not 
     */
    public void replaceWith(int victim, Frame incoming)
    {
        pm.set(victim, incoming);
    }

    /** method to get the frame at the current position (the position-nth frame), doesn't care if it is null or not */
    public Frame getFrameAt(int position)
    {
        return pm.get(position);
    }
}

/**
 * FIFO Memory Manager
 *
 * CS 406 Operating Systems
 * Spring 2016, Lafayette College
 * Professor Amir Sadovnik
 *
 * Project 3
 *
 * Renan Dincer and Ivan Evtimov
 *
 *
 * @author Renan Dincer
 * @author Ivan Evtimov
 *
 */

import java.util.*;

public class FIFOMemoryManager extends MemoryManager
{
    private LinkedList<Frame> fifo;

    public FIFOMemoryManager() throws ParametersUninitializedException
    {
        super();
        fifo= new LinkedList<Frame>();
    }

    protected void replacementHandler(int victimFrameNum, Frame incomingFrame) 
    {
        fifo.add(incomingFrame); //add to the end of the list

        Frame victim = physMem.getFrameAt(victimFrameNum);
        if(victim != null)
        {
            victim.clearAll();
        }

        incomingFrame.setNumber(victimFrameNum);

        physMem.replaceWith(victimFrameNum, incomingFrame);
    }

    protected Frame chooseVictim() 
    {
        return fifo.poll();
    }
}

/**
 * Second Chance Memory Manager
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

public class SecondChanceMemoryManager extends MemoryManager
{
    private LinkedList<Frame> fifo;

    public SecondChanceMemoryManager() throws ParametersUninitializedException
    {
        super();
        fifo = new LinkedList<Frame>();
    }

    protected void replacementHandler(int victimFrameNum, Frame incomingFrame) 
    {
        fifo.add(incomingFrame);

        Frame victim = physMem.getFrameAt(victimFrameNum);

        if(victim != null)
        {
            victim.clearAll();
        }

        incomingFrame.setNumber(victimFrameNum);

        physMem.replaceWith(victimFrameNum, incomingFrame);


    }

    protected  Frame chooseVictim() 
    {
        Frame candidateVictim = fifo.poll();

        while(candidateVictim.reference())
        {
            candidateVictim.clearReference();
            fifo.add(candidateVictim);
            candidateVictim = fifo.poll();
        }

        return candidateVictim;
    }

}

/**
 * Least Recently Used Memory Manager
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

public class LRUMemoryManager extends MemoryManager
{
	private LinkedList<Frame> lru;

    public LRUMemoryManager() throws ParametersUninitializedException
    {
        super();
		lru = new LinkedList<Frame>();
    }
    protected void replacementHandler(int victimFrameNum, Frame incomingFrame)
	{
	
		Frame victim = physMem.getFrameAt(victimFrameNum);
		if (victim != null)
		{
			victim.clearAll();
		}

		incomingFrame.setNumber(victimFrameNum);

		physMem.replaceWith(victimFrameNum, incomingFrame);
	}

    protected  Frame chooseVictim()
	{
		return lru.poll();
	}


	public int reference(Frame incomingFrame)
    {
        memAccessCount++; //statistics-keeping
        
        int victimFrameNum = -1;

        incomingFrame.setReference();

        // if it is valid, we are done, no disk access
        if(!incomingFrame.isValid())
        {
            pageFaultCount++;//statistics-keeping

            int freeFrameNum = physMem.getFreeFrame();

            if(freeFrameNum > -1)
            {
                victimFrameNum = freeFrameNum;           
                System.out.print("no replacement: ");
            }
            else
            {
                victimFrameNum = this.chooseVictim().getNumber();
                System.out.print("replacement: ");
                if(physMem.getFrameAt(victimFrameNum).isDirty())
                {
                    System.out.print(" needed to write " + victimFrameNum + " to disk: ");
                }
            }
            
            //the replacement handler is left to the children to implement - they might need to 
            //keep special track of who is being accessed, etc
            replacementHandler(victimFrameNum, incomingFrame);

        }

		lru.remove(incomingFrame);
		lru.add(incomingFrame);


        return victimFrameNum; //if a page was loaded, this would be set to something other than -1
    }

}

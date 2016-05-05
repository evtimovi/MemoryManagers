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
		lru.remove(incomingFrame);
		lru.add(incomingFrame);

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

}

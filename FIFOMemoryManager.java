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
	
	LinkedList<Frame> fifo= new LinkedList<Frame>()
    protected void replacementHandler(int victimFrameNum, Frame incomingFrame) {
		fifo.add(incomingFrame); //add to the end of the list
	}

   protected  Frame chooseVictim() {
		return fifo.poll();
	}

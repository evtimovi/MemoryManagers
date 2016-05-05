/**
 * Custom Memory Manager
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

public class CustomMemoryManager extends MemoryManager
{

    public CustomMemoryManager() throws ParametersUninitializedException
    {
        super();
    }

    protected void replacementHandler(int victimFrameNum, Frame incomingFrame) {}

    protected  Frame chooseVictim() {return null;}
}

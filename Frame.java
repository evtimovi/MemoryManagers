/**
 * represents a frame in OR OUT of main memory, the latter being considered an invalid frame.
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

public class Frame
{
    /**
     * this number here has a dual purpose:
     * 1. it keeps track of where in the PhysicalMemory object this Frame is located
     * 2. it determines if the Frame is in PhysicalMemory at all:
     *  - if set to -1, it is considered invalid and therefore not in PhysicalMemory
     */
    private int number;

    /**
     * the owning process' pid
     */
    private int pid;

    /**
     * exactly what one would expect - keeps track of whether something has been written to this page
     */
    private boolean dirty;

    //default constructor with number = -1 and dirty = false
    public Frame()
    {
        this.number = -1;
        this.dirty = false;
    }

    /**
     * same as default but allows to set the owner of the frame (the process' pid)
     */
    public Frame(int owner)
    {
        this.number = -1;
        this.dirty = false;
        this.pid = owner;
    }

    /**
     * set and clear dirty bit, both return the resulting value of the dirty bit
     */
    public boolean setDirtyBit(){ dirty = true; return dirty; }
    public boolean clearDirtyBit(){ dirty = false; return dirty; }

    /**
     * access the frame's number, -1 indicates it is not loaded in main memory (it is invalid)
     */
    public int getNumber(){ return number; }
    public boolean isValid(){ return number > -1; }

    /** reset the Frame to its default state, e.g. for when we are moving it out of memory */
    public void clearAll()
    {
        number = -1;
        dirty = false;
    }

    public void setNumber(int num)
    {
        this.number = num;
    }

    /** 3 different access methods based on different spelling conventions */
    public int getPid(){ return pid; }
    public int getPID(){ return pid; }
    public int getOwner(){ return pid; }
    public boolean isDirty(){ return dirty; }
}

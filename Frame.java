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
     * exactly what one would expect - keeps track of whether something has been written to this page
     */
    private boolean dirty;

    /**
     * TO-DO: abstractions for access and modification
     */

}

# Multi-Threaded producer consumer copy program
This is a program that runs 2 threads.
A producer to read from the input file and write to the buffer
A consumer to read from the buffer and write to the output file

This program demonstrates:
- Multi-threaded programming in java
- Using semaphores
- Synchronizing threads so they will not be able to read from an empty buffer, or write to a full buffer

package org.projectdsl.file.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * FileMerger is a central piece of projectdsl which takes a base file and add the content from a second file.
 * The merging is done by scanning line by line for matching lines and append chunks from the same area together.
 *
 */
public interface FileMerger {
	/**
	 * Receives a base file and a file to add content from and output the results to the writer.
	 * Notice that this method does not close the writer, caller's responsibility
	 * 
	 * @param base
	 * @param add
	 * @param out
	 */
	public void merge(Scanner base, Scanner add, BufferedWriter out) throws IOException;
}

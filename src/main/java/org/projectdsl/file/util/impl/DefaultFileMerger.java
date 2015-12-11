package org.projectdsl.file.util.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.projectdsl.file.util.FileMerger;

/**
 * Default implementation of FilterMerger
 *
 */
public class DefaultFileMerger implements FileMerger {
	
	/**
	 * Load all lines from a provided Scanner.
	 * @param file
	 * @return
	 */
	protected String[] loadAllLines(Scanner file) {
		List<String> lines = new ArrayList<>();
		while(file.hasNextLine()) {
			lines.add(file.nextLine());
		}
		return lines.toArray(new String[]{});
	}
	
	/**
	 * Provided a start and an end index, returns the block of lines for that range
	 * @param lines
	 * @param start
	 * @param end
	 * @return
	 */
	protected String[] linesBlock( String[] lines, int start, int end) {
		List<String> block = new ArrayList<>(end-start);
		for(int i = start; i < Math.min(lines.length,end); i++) {
			block.add(lines[i]);
		}
		return block.toArray(new String[]{});
	}
	
	/**
	 * Receives a line and checks if a match was found, if found returns a block of text, otherwise returns null
	 * @param line
	 * @return
	 */
	protected String[] foundBlock( String[] lines, String match, int start ) {
		int found = -1;
		for(int i = start; i < lines.length; i++) {
			String line = lines[i];
			if( line.replaceAll("\\s+", "").equalsIgnoreCase(match.replaceAll("\\s+", "")) ) {
				found = i;
			}
		}
		if(found > -1) {
			return linesBlock(lines, start, found);
		}
		return null;
	}

	@Override
	public void merge(Scanner base, Scanner add, BufferedWriter out) throws IOException {
		String[] addLines = loadAllLines(add);
		int start = 0;
		while( base.hasNextLine() ) {
			String baseLine = base.nextLine();
			String[] block = foundBlock(addLines, baseLine, start);
			if( block != null ) {
				start += block.length;
				for( int i = 0 ; i < block.length; i++) {
					out.write(block[i]);
				}
			}
			out.write(baseLine);
		}
	}

}

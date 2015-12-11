package org.projectdsl.file.util.impl

import groovy.mock.interceptor.MockFor
import spock.lang.Specification

class DefaultFileMergerTest extends Specification {
	def "linesBlock returns all lines"() {
		setup:
			def merger = new DefaultFileMerger()
		when:
			def lines = ["Hello","My","Friend"] as String[]
			def expected = ["Hello","My","Friend"] as String[]
			def result = merger.linesBlock(lines, 0, 3);
		then:
			expected == result
	}
	
	def "linesBlock returns inner lines"() {
		setup:
			def merger = new DefaultFileMerger()
		when:
			def lines = ["Hello","My","Friend"] as String[]
			def expected = ["My"] as String[]
			def result = merger.linesBlock(lines, 1, 2);
		then:
			expected == result
	}
	
	def "loadAllLines returns all lines"() {
		setup:
			def merger = new DefaultFileMerger()
			def scanner = GroovyMock(Scanner)
			scanner.hasNextLine() >>> [true,true,true,false]
			scanner.nextLine() >>> ["Hello","My","Friend",null]
			def expected = ["Hello","My","Friend"]
		when:
			merger.loadAllLines(scanner)
		then:
			1 * merger.loadAllLines(scanner) >> expected
	}
}

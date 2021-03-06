[![Build Status](https://travis-ci.org/ryanco/x12-parser.svg?branch=master)](https://travis-ci.org/ryanco/x12-parser)
[![Documentation Status](https://readthedocs.org/projects/x12-parser/badge/?version=latest)](http://x12-parser.readthedocs.org/en/latest/?badge=latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.yarsquidy/x12-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.yarsquidy/x12-parser)
X12 Parser
==========
Current Version: V2.0.0

CHANGES
-------
### V2.0.0
* Forked the code from Google Code to Github.
* Changed the namespace to support releasing to Maven Central.
* Added Sphinx to manage docs and support ReadTheDocs.
* Cleaned up code analysis findings from Intellij.
* Fixed javadoc generation warnings.
* Added unit tests for non-happy day cases.
* Added detection for ISA control header from issue #6.
* Added documentation to ReadTheDocs, removed javadoc in repo.

### V1.0
* Handle special characters that can be used as segment separators.
* Add removeElement(int index) added to Segment class.
* Add removeSegment(int index) added to X12Simple class.
* Add removeSegment(int index) added to Loop class.
* Add removeLoop(int index) added to Loop class.
* Add toString(boolean bRemoveTrailingEmptyElements) added to Segment class.
* Add toString(boolean bRemoveTrailingEmptyElements) added to X12Simple class.
* Add toString(boolean bRemoveTrailingEmptyElements) added to Loop class.
* Add toXML(boolean bRemoveTrailingEmptyElements) added to Segment class.
* Add toXML(boolean bRemoveTrailingEmptyElements) added to X12Simple class.
* Add toXML(boolean bRemoveTrailingEmptyElements) added to Loop class.
* Replace StringBuffer with StringBuilder.
* Add pom.xml for those who want to build with maven.

### V0.9
* Fixed problem with X12Parser. Loop detection logic was not working correctly.
* Backward compatible with versions V0.7 and V0.8.
* Recommended upgrade for users of V0.7 and V0.8 

### V0.8
* Fixed parsing problem in X12SimpleParser and X12Parser with parse(File file). 
* When reading ISA segment was being read twice. 
* Fixed issue with parse(InputStream in) method, that was causing blank spaces
* being padded at the end of IEA segment. 

### V0.7
* Not compatible with the previous versions.
* Earlier class X12 is now X12Simple. Provides the same features as earlier.
* Added class Loop and class X12. They represent the X12 in a loop 
* hierarchy.
* Parser is now an Interface. There are two types of Parsers, an X12SimpleParser
* and X12Parser. They return objects of type X12Simple and X12 respectively.
* New class Cf has configuration information of how to identify X12 loops.
* It is used in combination with the X12Parser.
* New examples have been added. 

### V0.6
* Second version

### V0.5
* Initial verision
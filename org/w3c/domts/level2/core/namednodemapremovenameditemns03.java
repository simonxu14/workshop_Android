
/*
This Java source file was generated by test-to-java.xsl
and is a derived work from the source document.
The source document contained the following notice:



Copyright (c) 2001 World Wide Web Consortium, 
(Massachusetts Institute of Technology, Institut National de
Recherche en Informatique et en Automatique, Keio University).  All 
Rights Reserved.  This program is distributed under the W3C's Software
Intellectual Property License.  This program is distributed in the 
hope that it will be useful, but WITHOUT ANY WARRANTY; without even
the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
PURPOSE.  

See W3C License http://www.w3.org/Consortium/Legal/ for more details.


*/

package org.w3c.domts.level2.core;

import org.w3c.dom.*;


import org.w3c.domts.DOMTestCase;
import org.w3c.domts.DOMTestDocumentBuilderFactory;



/**
 *    The method removeNamedItemNS removes a node specified by local name and namespace 
 *       
 *  Create a new element node and add 2 new attribute nodes to it that have the same localName
 *  but different namespaceURI's.  Remove the first attribute node from the namedNodeMap of the
 *  new element node and check to see that the second attribute still exists.
* @author IBM
* @author Neil Delima
* @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193</a>
*/
public final class namednodemapremovenameditemns03 extends DOMTestCase {

   /**
    * Constructor.
    * @param factory document factory, may not be null
    * @throws org.w3c.domts.DOMTestIncompatibleException Thrown if test is not compatible with parser configuration
    */
   public namednodemapremovenameditemns03(final DOMTestDocumentBuilderFactory factory)  throws org.w3c.domts.DOMTestIncompatibleException {

      org.w3c.domts.DocumentBuilderSetting[] settings = 
          new org.w3c.domts.DocumentBuilderSetting[] {
org.w3c.domts.DocumentBuilderSetting.namespaceAware
        };
        DOMTestDocumentBuilderFactory testFactory = factory.newInstance(settings);
        setFactory(testFactory);

    //
    //   check if loaded documents are supported for content type
    //
    String contentType = getContentType();
    preload(contentType, "staffNS", true);
    }

   /**
    * Runs the test case.
    * @throws Throwable Any uncaught exception causes test to fail
    */
   public void runTest() throws Throwable {
      Document doc;
      NamedNodeMap attributes;
      Node element;
      Attr attribute;
      Attr newAttribute;
      Attr attribute1;
      Attr attribute2;
      String nodeName;
      doc = (Document) load("staffNS", true);
      element = doc.createElementNS("http://www.w3.org/DOM/Test", "root");
      attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "L1:att");
      newAttribute = ((Element) /*Node */element).setAttributeNodeNS(attribute1);
      attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/L2", "L2:att");
      newAttribute = ((Element) /*Node */element).setAttributeNodeNS(attribute2);
      attributes = element.getAttributes();
      attribute = (Attr) attributes.removeNamedItemNS("http://www.w3.org/DOM/L1", "att");
      attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/DOM/L2", "att");
      nodeName = attribute.getNodeName();
      assertEquals("namednodemapremovenameditemns02", "L2:att", nodeName);
      }
   /**
    *  Gets URI that identifies the test.
    *  @return uri identifier of test
    */
   public String getTargetURI() {
      return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/namednodemapremovenameditemns03";
   }
   /**
    * Runs this test from the command line.
    * @param args command line arguments
    */
   public static void main(final String[] args) {
        DOMTestCase.doMain(namednodemapremovenameditemns03.class, args);
   }
}


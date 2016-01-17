package net.evalcode.javax.xml.bind;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import net.evalcode.javax.xml.bind.MapperFactory;
import net.evalcode.javax.xml.bind.XmlLinkedQueue;
import org.junit.Test;


/**
 * Test {@link XmlLinkedQueue}
 *
 * @author carsten.schipke@gmail.com
 */
public class XmlLinkedQueueTest
{
  // PREDEFINED PROPERTIES
  static final String JSON="[\"foo\",\"bar\"]";
  static final String XML=
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
    "<queue>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">foo</element>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">bar</element>" +
    "</queue>";


  // TESTS
  @Test
  public void testMarshal() throws IOException
  {
    assertEquals(JSON, MapperFactory.create(MapperFactory.Impl.JACKSON).marshal(
      new XmlLinkedQueue<String>() {{
        offer("foo");
        offer("bar");
      }}
    ));

    assertEquals(XML, MapperFactory.create(MapperFactory.Impl.JAXB).marshal(
      new XmlLinkedQueue<String>() {{
        offer("foo");
        offer("bar");
      }}
    ));
  }

  @Test
  @SuppressWarnings("all")
  public void testUnmarshal() throws IOException
  {
    final XmlLinkedQueue<String> queue0=MapperFactory.create(MapperFactory.Impl.JACKSON)
      .unmarshal(XmlLinkedQueue.class, JSON);

    assertNotNull(queue0);
    assertTrue(2==queue0.size());
    assertEquals("foo", queue0.poll());
    assertEquals("bar", queue0.poll());

    final XmlLinkedQueue<String> queue1=MapperFactory.create(MapperFactory.Impl.JAXB)
      .unmarshal(XmlLinkedQueue.class, XML);

    assertNotNull(queue1);
    assertTrue(2==queue1.size());
    assertEquals("foo", queue1.poll());
    assertEquals("bar", queue1.poll());
  }
}

package net.evalcode.javax.xml.bind;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import net.evalcode.javax.xml.bind.MapperFactory;
import net.evalcode.javax.xml.bind.XmlList;
import org.junit.Test;


/**
 * Test {@link XmlList}
 *
 * @author carsten.schipke@gmail.com
 */
public class XmlListTest
{
  // PREDEFINED PROPERTIES
  static final String JSON="[\"foo\",\"bar\"]";
  static final String XML=
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
    "<list>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">foo</element>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">bar</element>" +
    "</list>";


  // TESTS
  @Test
  public void testMarshal() throws IOException
  {
    assertEquals(JSON, MapperFactory.create(MapperFactory.Impl.JACKSON).marshal(
      new XmlList<String>() {{
        add("foo");
        add("bar");
      }}
    ));

    assertEquals(XML, MapperFactory.create(MapperFactory.Impl.JAXB).marshal(
      new XmlList<String>() {{
        add("foo");
        add("bar");
      }}
    ));
  }

  @Test
  @SuppressWarnings("all")
  public void testUnmarshal() throws IOException
  {
    final XmlList<String> list0=MapperFactory.create(MapperFactory.Impl.JACKSON)
      .unmarshal(XmlList.class, JSON);

    assertNotNull(list0);
    assertTrue(2==list0.size());
    assertEquals("foo", list0.get(0));
    assertEquals("bar", list0.get(1));

    final XmlList<String> list1=MapperFactory.create(MapperFactory.Impl.JAXB)
      .unmarshal(XmlList.class, XML);

    assertNotNull(list1);
    assertTrue(2==list1.size());
    assertEquals("foo", list1.get(0));
    assertEquals("bar", list1.get(1));
  }
}

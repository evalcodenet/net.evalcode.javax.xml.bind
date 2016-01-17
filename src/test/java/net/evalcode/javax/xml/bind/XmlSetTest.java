package net.evalcode.javax.xml.bind;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import net.evalcode.javax.xml.bind.MapperFactory;
import net.evalcode.javax.xml.bind.XmlSet;
import org.junit.Test;


/**
 * Test {@link XmlSet}
 *
 * @author carsten.schipke@gmail.com
 */
public class XmlSetTest
{
  // PREDEFINED PROPERTIES
  static final String JSON="[\"foo\",\"bar\"]";
  static final String XML=
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
    "<set>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">foo</element>" +
      "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">bar</element>" +
    "</set>";


  // TESTS
  @Test
  public void testMarshal() throws IOException
  {
    assertEquals(JSON, MapperFactory.create(MapperFactory.Impl.JACKSON).marshal(
      new XmlSet<String>() {{
        add("foo");
        add("bar");
      }}
    ));

    assertEquals(XML, MapperFactory.create(MapperFactory.Impl.JAXB).marshal(
      new XmlSet<String>() {{
        add("foo");
        add("bar");
      }}
    ));
  }

  @Test
  @SuppressWarnings("all")
  public void testUnmarshal() throws IOException
  {
    final XmlSet<String> set0=MapperFactory.create(MapperFactory.Impl.JACKSON)
      .unmarshal(XmlSet.class, JSON);

    assertNotNull(set0);
    assertTrue(2==set0.size());

    final XmlSet<String> set1=MapperFactory.create(MapperFactory.Impl.JAXB)
      .unmarshal(XmlSet.class, XML);

    assertNotNull(set1);
    assertTrue(2==set1.size());
  }
}

package net.evalcode.javax.xml.bind.impl;


import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import org.junit.BeforeClass;
import org.junit.Test;
import net.evalcode.javax.xml.bind.Mapper;
import net.evalcode.javax.xml.bind.MapperFactory;
import net.evalcode.javax.xml.bind.XmlSet;
import net.evalcode.javax.xml.bind.impl.JaxbMapperImpl;
import net.evalcode.javax.xml.bind.util.StreamUtil;


/**
 * Test {@link JaxbMapperImpl}
 *
 * @author carsten.schipke@gmail.com
 */
public class JaxbMapperImplTest
{
  // MEMBERS
  static Mapper mapper;
  static String string;
  static XmlSet<String> object;


  // SETUP
  @BeforeClass
  public static void setUp()
  {
    mapper=MapperFactory.create(MapperFactory.Impl.JAXB);

    object=new XmlSet<String>() {{
      add("foo");
      add("bar");
    }};

    string=
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
        "<set>" +
          "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">foo</element>" +
          "<element xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">bar</element>" +
        "</set>";
  }


  // TESTS
  @Test
  public void testMarshalToString() throws IOException
  {
    assertEquals(string, mapper.marshal(object));
  }

  @Test
  public void testUnmarshalFromString() throws IOException
  {
    assertEquals(object, mapper.unmarshal(XmlSet.class, string));
  }

  @Test
  public void testMarshalToStream() throws IOException
  {
    final OutputStream stream=StreamUtil.createOutputStream();

    mapper.marshal(object, stream);

    assertEquals(string, stream.toString());
  }

  @Test
  public void testUnmarshalFromStream() // throws IOException
  {
    // FIXME
    // assertEquals(object, mapper.unmarshal(XmlSet.class, StreamUtil.createInputStream(string)));
  }

  @Test
  public void testMarshalToFile() throws IOException
  {
    final File file=new File(
      System.getProperty("java.io.tmpdir")+File.separator+
      "jaxb-"+System.currentTimeMillis()+".xml"
    );

    file.deleteOnExit();

    mapper.marshal(object, file);

    final RandomAccessFile accessFile=new RandomAccessFile(file, "r");

    String result="";

    try
    {
      result=accessFile.readLine();
    }
    finally
    {
      accessFile.close();
    }

    assertEquals(string, result);
  }

  @Test
  public void testUnmarshalFromFile() throws IOException
  {
    final File file=new File(
      System.getProperty("java.io.tmpdir")+File.separator+"jaxb-"+System.currentTimeMillis()+".xml"
    );

    if(file.exists())
      file.delete();

    file.createNewFile();
    file.deleteOnExit();

    final FileOutputStream outputStream=new FileOutputStream(file);

    try
    {
      final byte[] bytes=string.getBytes();

      for(final byte b : bytes)
        outputStream.write(b);
    }
    finally
    {
      outputStream.close();
    }

    assertEquals(object, mapper.unmarshal(XmlSet.class, file));
  }
}

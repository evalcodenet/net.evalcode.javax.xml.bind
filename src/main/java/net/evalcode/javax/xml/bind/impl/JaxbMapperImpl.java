package net.evalcode.javax.xml.bind.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.evalcode.javax.xml.bind.Mapper;
import net.evalcode.javax.xml.bind.XmlLinkedQueue;
import net.evalcode.javax.xml.bind.XmlList;
import net.evalcode.javax.xml.bind.XmlSet;


/**
 * JaxbMapperImpl
 *
 * @author carsten.schipke@gmail.com
 */
public class JaxbMapperImpl implements Mapper
{
  // MEMBERS
  final Marshaller marshaller;
  final Unmarshaller unmarshaller;


  // CONSTRUCTION
  @Inject
  public JaxbMapperImpl(final JAXBContext context) throws JAXBException
  {
    marshaller=context.createMarshaller();
    unmarshaller=context.createUnmarshaller();
  }

  public JaxbMapperImpl() throws JAXBException
  {
    this(JAXBContext.newInstance(
      new Class<?>[] {
        XmlList.class,
        XmlLinkedQueue.class,
        XmlSet.class
      }
    ));
  }


  // ACCESSORS/MUTATORS
  @Override
  public void marshal(final Object object, final OutputStream output) throws IOException
  {
    try
    {
      marshaller.marshal(object, output);
    }
    catch(final JAXBException e)
    {
      throw new IOException(e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T unmarshal(final Class<T> type, final InputStream input) throws IOException
  {
    try
    {
      return (T)JAXBContext.newInstance(type).createUnmarshaller().unmarshal(input);
    }
    catch(JAXBException e)
    {
      throw new IOException(e);
    }
  }

  @Override
  public String marshal(final Object object) throws IOException
  {
    final StringBuffer stringBuffer=new StringBuffer();

    try
    {
      marshaller.marshal(object, new OutputStream() {
        @Override
        public void write(final int b) throws IOException
        {
          stringBuffer.append((char)b);
        }
      });
    }
    catch(final JAXBException e)
    {
      throw new IOException(e);
    }

    return stringBuffer.toString();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T unmarshal(final Class<T> type, final String input) throws IOException
  {
    final byte[] bytes=input.getBytes();
    final int length=bytes.length;

    final AtomicInteger position=new AtomicInteger();

    try
    {
      return (T)unmarshaller.unmarshal(new InputStream() {
        @Override
        public int read() throws IOException
        {
          if(position.get()<length)
            return bytes[position.getAndIncrement()];

          return -1;
        }
      });
    }
    catch(final JAXBException e)
    {
      throw new IOException(e);
    }
  }

  @Override
  public void marshal(final Object object, final File output) throws IOException
  {
    final FileOutputStream outputStream=new FileOutputStream(output);

    try
    {
      marshal(object, outputStream);
    }
    finally
    {
      outputStream.close();
    }
  }

  @Override
  public <T> T unmarshal(final Class<T> type, final File input) throws IOException
  {
    final FileInputStream inputStream=new FileInputStream(input);

    try
    {
      return unmarshal(type, inputStream);
    }
    finally
    {
      inputStream.close();
    }
  }
}

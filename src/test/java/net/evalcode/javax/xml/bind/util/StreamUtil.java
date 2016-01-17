package net.evalcode.javax.xml.bind.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Ignore;


/**
 * StreamUtil
 *
 * @author carsten.schipke@gmail.com
 */
@Ignore
public class StreamUtil
{
  // STATIC ACCESSORS
  public static InputStream createInputStream(final String value)
  {
    return new InputStream()
    {
      // MEMBERS
      final AtomicLong position=new AtomicLong(0);
      final byte[] bytes=value.getBytes();


      // OVERRIDES/IMPLEMENTS
      @Override
      public int read() throws IOException
      {
        if(bytes.length>position.get())
          return bytes[(int)position.getAndIncrement()];

        return -1;
      }

      @Override
      public int read(final byte[] buffer)
        throws IOException
      {
        return read(buffer, 0, buffer.length);
      }

      @Override
      public int read(final byte[] buffer, final int offset, final int length)
        throws IOException
      {
        int read=0;
        final int count=length-offset;

        for(; read<count; read++)
        {
          int current=read();

          if(-1==current)
            break;

          buffer[offset+read]=(byte)current;
        }

        return read;
      }


      @Override
      public long skip(final long n)
      {
        return position.addAndGet(n);
      }

      @Override
      public int available()
      {
        return bytes.length-(int)position.get();
      }
    };
  }

  public static OutputStream createOutputStream()
  {
    return new OutputStream()
    {
      // MEMBERS
      final StringBuilder stringBuilder=new StringBuilder();


      // OVERRIDES/IMPLEMENTS
      @Override
      public void write(final int b) throws IOException
      {
        stringBuilder.append((char)b);
      }

      @Override
      public void write(final byte[] buffer)
      {
        write(buffer, 0, buffer.length);
      }

      @Override
      public void write(final byte[] buffer, final int offset, final int length)
      {
        final int limit=offset+length;

        for(int i=offset; i<limit; i++)
          stringBuilder.append((char)buffer[i]);
      }

      @Override
      public String toString()
      {
        return stringBuilder.toString();
      }
    };
  }
}

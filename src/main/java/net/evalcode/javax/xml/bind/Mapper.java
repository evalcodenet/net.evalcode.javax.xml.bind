package net.evalcode.javax.xml.bind;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Mapper
 *
 * <p> API for JAX-B mapper implementations.
 *
 * @author carsten.schipke@gmail.com
 */
public interface Mapper
{
  // ACCESSORS/MUTATORS
  String marshal(Object object) throws IOException;
  <T> T unmarshal(Class<T> type, String input) throws IOException;

  void marshal(Object object, OutputStream output) throws IOException;
  <T> T unmarshal(Class<T> type, InputStream input) throws IOException;

  void marshal(Object object, File output) throws IOException;
  <T> T unmarshal(Class<T> type, File input) throws IOException;
}

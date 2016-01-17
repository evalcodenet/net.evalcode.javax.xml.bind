package net.evalcode.javax.xml.bind.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import net.evalcode.javax.xml.bind.Mapper;


/**
 * JacksonMapperImpl
 *
 * @author carsten.schipke@gmail.com
 */
public class JacksonMapperImpl implements Mapper
{
  // MEMBERS
  final ObjectMapper objectMapper;


  // CONSTRUCTION
  @Inject
  public JacksonMapperImpl(final ObjectMapper objectMapper)
  {
    this.objectMapper=objectMapper;
  }

  public JacksonMapperImpl()
  {
    this(new ObjectMapper());

    final JaxbAnnotationIntrospector jaxbAnnotationIntrospector=
      new JaxbAnnotationIntrospector();

    final JacksonAnnotationIntrospector jacksonAnnotationIntrospector=
      new JacksonAnnotationIntrospector();

    objectMapper.getDeserializationConfig().setAnnotationIntrospector(
      AnnotationIntrospector.pair(jaxbAnnotationIntrospector, jacksonAnnotationIntrospector)
    );

    objectMapper.getSerializationConfig().setAnnotationIntrospector(
      AnnotationIntrospector.pair(jaxbAnnotationIntrospector, jacksonAnnotationIntrospector)
    );
  }


  // ACCESSORS/MUTATORS
  @Override
  public void marshal(final Object object, final OutputStream output) throws IOException
  {
    objectMapper.writeValue(output, object);
  }

  @Override
  public <T> T unmarshal(final Class<T> type, final InputStream input) throws IOException
  {
    return objectMapper.readValue(input, type);
  }

  @Override
  public String marshal(final Object object) throws IOException
  {
    return objectMapper.writeValueAsString(object);
  }

  @Override
  public <T> T unmarshal(final Class<T> type, final String input) throws IOException
  {
    return objectMapper.readValue(input, type);
  }

  @Override
  public void marshal(final Object object, final File output) throws IOException
  {
    objectMapper.writeValue(output, object);
  }

  @Override
  public <T> T unmarshal(final Class<T> type, final File input) throws IOException
  {
    return objectMapper.readValue(input,  type);
  }
}

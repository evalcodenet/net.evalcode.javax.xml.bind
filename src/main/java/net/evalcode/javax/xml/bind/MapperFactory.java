package net.evalcode.javax.xml.bind;


/**
 * MapperFactory
 *
 * @author carsten.schipke@gmail.com
 */
public final class MapperFactory
{
  /**
   * Impl
   *
   * @author carsten.schipke@gmail.com
   */
  public static enum Impl
  {
    // PREDEFINED IMPLEMENTATIONS
    JACKSON("net.evalcode.javax.xml.bind.impl.JacksonMapperImpl"),
    JAXB("net.evalcode.javax.xml.bind.impl.JaxbMapperImpl");


    // MEMBERS
    final String value;


    // CONSTRUCTION
    Impl(final String value)
    {
      this.value=value;
    }
  }


  // CONSTRUCTION
  private MapperFactory()
  {
    super();
  }


  // STATIC ACCESSORS
  /**
   * @param Impl JAX-B Mapper Implementation.
   *
   * @return {@link Mapper}
   */
  public static Mapper create(final Impl impl)
  {
    try
    {
      final Class<?> clazz=Class.forName(impl.value);

      if(null==clazz)
        throw new ClassNotFoundException();

      return (Mapper)clazz.newInstance();
    }
    catch(final ReflectiveOperationException e)
    {
      throw new IllegalArgumentException(
        String.format("Failed to create instance of Mapper [%1$s].", impl.value), e
      );
    }
  }
}

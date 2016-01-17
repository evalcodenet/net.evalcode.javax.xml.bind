package net.evalcode.javax.xml.bind;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.google.common.collect.ForwardingSet;


/**
 * XmlSet
 *
 * <p> Wrapped HashSet for XML binding.
 *
 * @author carsten.schipke@gmail.com
 * @see java.util.HashSet
 * @param <E> The type of elements held in this collection.
 */
@XmlType
@XmlRootElement(name="set")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlSet<E> extends ForwardingSet<E> implements Serializable
{
  // PREDEFINED PROPERTIES
  static final long serialVersionUID=1L;


  // MEMBERS
  @XmlElement(name="element")
  final Set<E> set=new HashSet<E>();


  // OVERRIDES/IMPLEMENTS
  @Override
  protected Set<E> delegate()
  {
    return set;
  }
}

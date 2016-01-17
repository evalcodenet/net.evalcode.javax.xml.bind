package net.evalcode.javax.xml.bind;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.google.common.collect.ForwardingList;


/**
 * XmlList
 *
 * <p> Wrapped ArrayList for XML binding.
 *
 * @author carsten.schipke@gmail.com
 * @see java.util.ArrayList
 * @param <E> The type of elements held in this collection.
 */
@XmlType
@XmlRootElement(name="list")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlList<E> extends ForwardingList<E> implements Serializable
{
  // PREDEFINED PROPERTIES
  static final long serialVersionUID=1L;


  // MEMBERS
  @XmlElement(name="element")
  final List<E> list=new ArrayList<E>();


  // OVERRIDES/IMPLEMENTS
  @Override
  protected List<E> delegate()
  {
    return list;
  }
}

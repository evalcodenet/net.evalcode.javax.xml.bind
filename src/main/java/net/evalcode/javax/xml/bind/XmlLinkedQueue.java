package net.evalcode.javax.xml.bind;


import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.google.common.collect.ForwardingQueue;


/**
 * XmlLinkedQueue
 *
 * <p> Wrapped ConcurrentLinkedQueue for XML binding.
 *
 * @author carsten.schipke@gmail.com
 * @see java.util.concurrent.ConcurrentLinkedQueue
 * @param <E> The type of elements held in this collection.
 */
@XmlType
@XmlRootElement(name="queue")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlLinkedQueue<E> extends ForwardingQueue<E> implements Serializable
{
  // PREDEFINED PROPERTIES
  static final long serialVersionUID=1L;


  // MEMBERS
  @XmlElement(name="element")
  final Queue<E> queue=new ConcurrentLinkedQueue<E>();


  // OVERRIDES/IMPLEMENTS
  @Override
  protected Queue<E> delegate()
  {
    return queue;
  }
}

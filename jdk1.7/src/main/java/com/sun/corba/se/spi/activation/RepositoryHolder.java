package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/RepositoryHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../../../../src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Friday, April 10, 2015 7:48:30 PM PDT
*/

public final class RepositoryHolder implements org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.spi.activation.Repository value = null;

  public RepositoryHolder ()
  {
  }

  public RepositoryHolder (com.sun.corba.se.spi.activation.Repository initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.spi.activation.RepositoryHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.spi.activation.RepositoryHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.spi.activation.RepositoryHelper.type ();
  }

}

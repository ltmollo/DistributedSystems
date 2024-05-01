//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `smarthome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Smarthome;

/**
 * Helper class for marshaling/unmarshaling coffeeTypes.
 **/
public final class coffeeTypesHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, java.util.List<CoffeeType> v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.size());
            for(CoffeeType elem : v)
            {
                CoffeeType.ice_write(ostr, elem);
            }
        }
    }

    public static java.util.List<CoffeeType> read(com.zeroc.Ice.InputStream istr)
    {
        final java.util.List<CoffeeType> v;
        v = new java.util.ArrayList<CoffeeType>();
        final int len0 = istr.readAndCheckSeqSize(1);
        for(int i0 = 0; i0 < len0; i0++)
        {
            CoffeeType elem;
            elem = CoffeeType.ice_read(istr);
            v.add(elem);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<java.util.List<CoffeeType>> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.List<CoffeeType> v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            coffeeTypesHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<java.util.List<CoffeeType>> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            java.util.List<CoffeeType> v;
            v = coffeeTypesHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}

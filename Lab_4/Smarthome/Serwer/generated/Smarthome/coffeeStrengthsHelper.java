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
 * Helper class for marshaling/unmarshaling coffeeStrengths.
 **/
public final class coffeeStrengthsHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, java.util.List<CoffeeStrength> v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.size());
            for(CoffeeStrength elem : v)
            {
                CoffeeStrength.ice_write(ostr, elem);
            }
        }
    }

    public static java.util.List<CoffeeStrength> read(com.zeroc.Ice.InputStream istr)
    {
        final java.util.List<CoffeeStrength> v;
        v = new java.util.ArrayList<CoffeeStrength>();
        final int len0 = istr.readAndCheckSeqSize(1);
        for(int i0 = 0; i0 < len0; i0++)
        {
            CoffeeStrength elem;
            elem = CoffeeStrength.ice_read(istr);
            v.add(elem);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<java.util.List<CoffeeStrength>> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.List<CoffeeStrength> v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            coffeeStrengthsHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<java.util.List<CoffeeStrength>> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            java.util.List<CoffeeStrength> v;
            v = coffeeStrengthsHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}

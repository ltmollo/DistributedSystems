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

public class InvalidChannelException extends SmarthomeException
{
    public InvalidChannelException()
    {
        super();
    }

    public InvalidChannelException(Throwable cause)
    {
        super(cause);
    }

    public InvalidChannelException(String message)
    {
        super(message);
    }

    public InvalidChannelException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public String ice_id()
    {
        return "::Smarthome::InvalidChannelException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Smarthome::InvalidChannelException", -1, false);
        ostr_.endSlice();
        super._writeImpl(ostr_);
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
        super._readImpl(istr_);
    }

    /** @hidden */
    public static final long serialVersionUID = 2070805684L;
}

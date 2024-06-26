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

public class TurnDeviceException extends SmarthomeException
{
    public TurnDeviceException()
    {
        super();
    }

    public TurnDeviceException(Throwable cause)
    {
        super(cause);
    }

    public TurnDeviceException(String message)
    {
        super(message);
    }

    public TurnDeviceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public String ice_id()
    {
        return "::Smarthome::TurnDeviceException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Smarthome::TurnDeviceException", -1, false);
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
    public static final long serialVersionUID = -347967581L;
}

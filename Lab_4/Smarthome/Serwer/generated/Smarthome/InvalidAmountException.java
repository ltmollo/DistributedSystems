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

public class InvalidAmountException extends SmarthomeException
{
    public InvalidAmountException()
    {
        super();
    }

    public InvalidAmountException(Throwable cause)
    {
        super(cause);
    }

    public InvalidAmountException(String message)
    {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public String ice_id()
    {
        return "::Smarthome::InvalidAmountException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Smarthome::InvalidAmountException", -1, false);
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
    public static final long serialVersionUID = 1783479931L;
}
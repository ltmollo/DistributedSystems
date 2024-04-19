//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `calculator.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public class EmptySequence extends com.zeroc.Ice.UserException
{
    public EmptySequence()
    {
        this.reason = "Empty sequence";
    }

    public EmptySequence(Throwable cause)
    {
        super(cause);
        this.reason = "Empty sequence";
    }

    public EmptySequence(String reason)
    {
        this.reason = reason;
    }

    public EmptySequence(String reason, Throwable cause)
    {
        super(cause);
        this.reason = reason;
    }

    public String ice_id()
    {
        return "::Demo::EmptySequence";
    }

    public String reason;

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Demo::EmptySequence", -1, true);
        ostr_.writeString(reason);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        reason = istr_.readString();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = 1209636641L;
}

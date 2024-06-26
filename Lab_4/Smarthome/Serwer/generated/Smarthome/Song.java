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

public class Song implements java.lang.Cloneable,
                             java.io.Serializable
{
    public String title;

    public String artist;

    public double duration;

    public Genre genre;

    public Song()
    {
        this.title = "";
        this.artist = "";
        this.genre = Genre.ROCK;
    }

    public Song(String title, String artist, double duration, Genre genre)
    {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Song r = null;
        if(rhs instanceof Song)
        {
            r = (Song)rhs;
        }

        if(r != null)
        {
            if(this.title != r.title)
            {
                if(this.title == null || r.title == null || !this.title.equals(r.title))
                {
                    return false;
                }
            }
            if(this.artist != r.artist)
            {
                if(this.artist == null || r.artist == null || !this.artist.equals(r.artist))
                {
                    return false;
                }
            }
            if(this.duration != r.duration)
            {
                return false;
            }
            if(this.genre != r.genre)
            {
                if(this.genre == null || r.genre == null || !this.genre.equals(r.genre))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Smarthome::Song");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, title);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, artist);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, duration);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, genre);
        return h_;
    }

    public Song clone()
    {
        Song c = null;
        try
        {
            c = (Song)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.title);
        ostr.writeString(this.artist);
        ostr.writeDouble(this.duration);
        Genre.ice_write(ostr, this.genre);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.title = istr.readString();
        this.artist = istr.readString();
        this.duration = istr.readDouble();
        this.genre = Genre.ice_read(istr);
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, Song v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public Song ice_read(com.zeroc.Ice.InputStream istr)
    {
        Song v = new Song();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Song> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Song v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<Song> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(Song.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final Song _nullMarshalValue = new Song();

    /** @hidden */
    public static final long serialVersionUID = -19530364L;
}

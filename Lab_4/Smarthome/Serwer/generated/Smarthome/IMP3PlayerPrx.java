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

public interface IMP3PlayerPrx extends ISpeakerPrx
{
    default Song getSong()
    {
        return getSong(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Song getSong(java.util.Map<String, String> context)
    {
        return _iceI_getSongAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Song> getSongAsync()
    {
        return _iceI_getSongAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Song> getSongAsync(java.util.Map<String, String> context)
    {
        return _iceI_getSongAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Song> _iceI_getSongAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Song> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getSong", com.zeroc.Ice.OperationMode.Idempotent, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     Song ret;
                     ret = Song.ice_read(istr);
                     return ret;
                 });
        return f;
    }

    default boolean setSong(Song song)
        throws InvalidSongException
    {
        return setSong(song, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean setSong(Song song, java.util.Map<String, String> context)
        throws InvalidSongException
    {
        try
        {
            return _iceI_setSongAsync(song, context, true).waitForResponseOrUserEx();
        }
        catch(InvalidSongException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> setSongAsync(Song song)
    {
        return _iceI_setSongAsync(song, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> setSongAsync(Song song, java.util.Map<String, String> context)
    {
        return _iceI_setSongAsync(song, context, false);
    }

    /**
     * @hidden
     * @param iceP_song -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_setSongAsync(Song iceP_song, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setSong", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_setSong);
        f.invoke(true, context, null, ostr -> {
                     Song.ice_write(ostr, iceP_song);
                 }, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setSong =
    {
        InvalidSongException.class
    };

    default boolean pause()
        throws PauseMP3Exception
    {
        return pause(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean pause(java.util.Map<String, String> context)
        throws PauseMP3Exception
    {
        try
        {
            return _iceI_pauseAsync(context, true).waitForResponseOrUserEx();
        }
        catch(PauseMP3Exception ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> pauseAsync()
    {
        return _iceI_pauseAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> pauseAsync(java.util.Map<String, String> context)
    {
        return _iceI_pauseAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_pauseAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "pause", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_pause);
        f.invoke(true, context, null, null, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_pause =
    {
        PauseMP3Exception.class
    };

    default boolean unpause()
        throws PauseMP3Exception
    {
        return unpause(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean unpause(java.util.Map<String, String> context)
        throws PauseMP3Exception
    {
        try
        {
            return _iceI_unpauseAsync(context, true).waitForResponseOrUserEx();
        }
        catch(PauseMP3Exception ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> unpauseAsync()
    {
        return _iceI_unpauseAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> unpauseAsync(java.util.Map<String, String> context)
    {
        return _iceI_unpauseAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_unpauseAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "unpause", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_unpause);
        f.invoke(true, context, null, null, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_unpause =
    {
        PauseMP3Exception.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IMP3PlayerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IMP3PlayerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IMP3PlayerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IMP3PlayerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static IMP3PlayerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static IMP3PlayerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, IMP3PlayerPrx.class, _IMP3PlayerPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default IMP3PlayerPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (IMP3PlayerPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default IMP3PlayerPrx ice_adapterId(String newAdapterId)
    {
        return (IMP3PlayerPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default IMP3PlayerPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (IMP3PlayerPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default IMP3PlayerPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (IMP3PlayerPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default IMP3PlayerPrx ice_invocationTimeout(int newTimeout)
    {
        return (IMP3PlayerPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default IMP3PlayerPrx ice_connectionCached(boolean newCache)
    {
        return (IMP3PlayerPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default IMP3PlayerPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (IMP3PlayerPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default IMP3PlayerPrx ice_secure(boolean b)
    {
        return (IMP3PlayerPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default IMP3PlayerPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (IMP3PlayerPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default IMP3PlayerPrx ice_preferSecure(boolean b)
    {
        return (IMP3PlayerPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default IMP3PlayerPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (IMP3PlayerPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default IMP3PlayerPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (IMP3PlayerPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default IMP3PlayerPrx ice_collocationOptimized(boolean b)
    {
        return (IMP3PlayerPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default IMP3PlayerPrx ice_twoway()
    {
        return (IMP3PlayerPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default IMP3PlayerPrx ice_oneway()
    {
        return (IMP3PlayerPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default IMP3PlayerPrx ice_batchOneway()
    {
        return (IMP3PlayerPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default IMP3PlayerPrx ice_datagram()
    {
        return (IMP3PlayerPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default IMP3PlayerPrx ice_batchDatagram()
    {
        return (IMP3PlayerPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default IMP3PlayerPrx ice_compress(boolean co)
    {
        return (IMP3PlayerPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default IMP3PlayerPrx ice_timeout(int t)
    {
        return (IMP3PlayerPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default IMP3PlayerPrx ice_connectionId(String connectionId)
    {
        return (IMP3PlayerPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default IMP3PlayerPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (IMP3PlayerPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Smarthome::IMP3Player";
    }
}

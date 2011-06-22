package matlabcontrol;

/*
 * Copyright (c) 2011, Joshua Kaplan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *  - Neither the name of matlabcontrol nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Allows for calling MATLAB from <b>inside</b> of MATLAB.
 * 
 * @since 3.1.0
 * 
 * @author <a href="mailto:nonother@gmail.com">Joshua Kaplan</a>
 */
class LocalMatlabProxy extends MatlabProxy
{   
    /**
     * If connected to MATLAB.
     * 
     * This notion of connection exists to make it consistent with {@link RemoteMatlabProxy}, but is not actually
     * necessary. Unless a user calls {@link #disconnect()} this proxy cannot become disconnected.
     */
    private volatile boolean _isConnected = true;

    LocalMatlabProxy(Identifier id)
    {
        super(id, true);
    }
    
    // Methods defined in MatlabProxy
    
    @Override
    public boolean isConnected()
    {
        return _isConnected;
    }
    
    @Override
    public boolean disconnect()
    {
        _isConnected = false;
        
        //Notify listeners
        notifyDisconnectionListeners();
        
        return true;
    }
    
    @Override
    public <T> T invokeAndWait(MatlabThreadCallable<T> callable) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            return JMIWrapper.invokeAndWait(callable);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
        
    @Override
    public void exit() throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            JMIWrapper.exit();
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
    
    // Methods defined in MatlabInteractor

    @Override
    public void eval(String command) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            JMIWrapper.eval(command);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
    
    @Override
    public Object returningEval(String command, int returnCount) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            return JMIWrapper.returningEval(command, returnCount);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
    
    @Override
    public void feval(String functionName, Object[] args) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            JMIWrapper.feval(functionName, args);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
    
    @Override
    public Object returningFeval(String functionName, Object[] args) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            return JMIWrapper.returningFeval(functionName, args);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }

    @Override
    public Object returningFeval(String functionName, Object[] args, int returnCount) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            return JMIWrapper.returningFeval(functionName, args, returnCount);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }

    @Override
    public void setVariable(String variableName, Object value) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            JMIWrapper.setVariable(variableName, value);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
    
    @Override
    public Object getVariable(String variableName) throws MatlabInvocationException
    {
        if(this.isConnected())
        {
            return JMIWrapper.getVariable(variableName);
        }
        else
        {
            throw new MatlabInvocationException(MatlabInvocationException.PROXY_NOT_CONNECTED_MSG);
        }
    }
}
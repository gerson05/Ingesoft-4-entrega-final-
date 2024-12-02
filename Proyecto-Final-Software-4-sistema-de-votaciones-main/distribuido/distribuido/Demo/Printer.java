//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `Printer.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public interface Printer extends com.zeroc.Ice.Object
{
    Response printString(String s, com.zeroc.Ice.Current current);

    void registerWorker(String workerId, WorkerPrx workerProxy, com.zeroc.Ice.Current current);

    void assignTaskToWorker(String taskId, String taskData, com.zeroc.Ice.Current current);

    void receivePartialResult(String taskId, String partialResult, com.zeroc.Ice.Current current);

    void notifyTaskCompletion(String taskId, com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Demo::Printer",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Demo::Printer";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_printString(Printer obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_s;
        iceP_s = istr.readString();
        inS.endReadParams();
        Response ret = obj.printString(iceP_s, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeValue(ret);
        ostr.writePendingValues();
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_registerWorker(Printer obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_workerId;
        WorkerPrx iceP_workerProxy;
        iceP_workerId = istr.readString();
        iceP_workerProxy = WorkerPrx.uncheckedCast(istr.readProxy());
        inS.endReadParams();
        obj.registerWorker(iceP_workerId, iceP_workerProxy, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_assignTaskToWorker(Printer obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_taskId;
        String iceP_taskData;
        iceP_taskId = istr.readString();
        iceP_taskData = istr.readString();
        inS.endReadParams();
        obj.assignTaskToWorker(iceP_taskId, iceP_taskData, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_receivePartialResult(Printer obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_taskId;
        String iceP_partialResult;
        iceP_taskId = istr.readString();
        iceP_partialResult = istr.readString();
        inS.endReadParams();
        obj.receivePartialResult(iceP_taskId, iceP_partialResult, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_notifyTaskCompletion(Printer obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_taskId;
        iceP_taskId = istr.readString();
        inS.endReadParams();
        obj.notifyTaskCompletion(iceP_taskId, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "assignTaskToWorker",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "notifyTaskCompletion",
        "printString",
        "receivePartialResult",
        "registerWorker"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_assignTaskToWorker(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_notifyTaskCompletion(this, in, current);
            }
            case 6:
            {
                return _iceD_printString(this, in, current);
            }
            case 7:
            {
                return _iceD_receivePartialResult(this, in, current);
            }
            case 8:
            {
                return _iceD_registerWorker(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}

namespace java cn.com.sand.component.rpc.thrift.idl

include "base.thrift"

struct McRequest
{
	1: string fromAddress,
	2: string routeInfo,
	3: string traceId,
	4: optional string msgType = "01",//01:请求，02:通知
	5: string data
}

struct McResponse
{
	1: optional string errorCode,
	2: optional string errorMsg,
	3: optional string errorSysCode,
	4: optional string data
}

service McService
{
	McResponse call(1:McRequest request) throws (1: base.McServiceException ex)
}

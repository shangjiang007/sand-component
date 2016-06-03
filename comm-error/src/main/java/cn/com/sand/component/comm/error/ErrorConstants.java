/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-error $Id$ $Revision$ Last Changed by SJ at
 * 2015年11月4日 下午4:55:28 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年11月4日
 * Initailized
 */
package cn.com.sand.component.comm.error;

/**
 *
 * @ClassName ：ErrorConstants
 * @author : SJ
 * @Date : 2015年11月4日 下午4:55:28
 * @version 2.0.0
 *
 */
public class ErrorConstants
{
    /** 多渠道拒绝码 */
    // public static final String ERROR_SYSID_MCREFUSECODE = "MCREFUSECODE";
    /** 多渠道内部应答码 */
    public static final String ERROR_SYSID_MCRESPONSECODE          = "MCRESPONSECODE";
    /** 支付通道端应答码 */
    // public static final String ERROR_SYSID_PCRESPONSECODE = "PCRESPONSECODE";
    /** 业务通道端应答码 */
    // public static final String ERROR_SYSID_BCRESPONSECODE = "BCRESPONSECODE";

    public static final String ERROR_SYSID_SANDCARD_RESPONSECODE   = "SANDCARDRESPONSECODE";
    public static final String ERROR_SYSID_MEMBERCARD_RESPONSECODE = "MEMBERCARDRESPONSECODE";
    public static final String ERROR_SYSID_SANDIFS_RESPONSECODE    = "SANDIFSRESPONSECODE";
    public static final String ERROR_SYSID_SAS_RESPONSECODE        = "SASRESPONSECODE";
    public static final String ERROR_SYSID_FAS_RESPONSECODE        = "FASRESPONSECODE";
    public static final String ERROR_SYSID_MASFRONT_RESPONSECODE  = "MASFRONTRESPONSECODE";
    public static final String ERROR_SYSID_UMS_RESPONSECODE  	   = "UMSRESPONSECODE";
    public static final String ERROR_SYSID_ABACUS_RESPONSECODE     = "ABACUSRESPONSECODE";
    public static final String ERROR_SYSID_VERIFICATION_RESPONSECODE     = "VERIFICATIONRESPONSECODE";
    public static final String ERROR_SYSID_SMPS_RESPONSECODE     = "SMPSRESPONSECODE";

    public static final String ERROR_REFUSCODE = "209999";

    /** 
     * use for database start
     */
    public static final String ERROR_MC_RESPONSE_CODE_SUCCESS                              = "000000";//库表响应码默认值
    public static final String ERROR_MC_RESPONSE_DESC_SUCCESS                              = "success";//库表响应码默认值
    public static final String ERROR_MC_RESPONSE_CODE_DEFAULT                              = "999999";//库表响应码默认值
    public static final String ERROR_MC_RESPONSE_DESC_DEFAULT                              = "DEFAULT";//库表响应码默认值
    /** 
     * use for database end
     */

    public static final String ERROR_MC_RESPONSE_SUCCESS                                   = "00000000";
    public static final String ERROR_MC_RESPONSE_EXCEPTION                                 = "20000001";
    public static final String ERROR_MC_RESPONSE_INTERCE_TIMEOUT                           = "20000002";
    public static final String ERROR_MC_RESPONSE_PAY_TIMEOUT                               = "20000003";
    public static final String ERROR_MC_RESPONSE_GATEWAY_TIMEOUT                           = "20000004";
    public static final String ERROR_MC_RESPONSE_GATEWAY_NONE                              = "20000005";
    public static final String ERROR_MC_RESPONSE_SIGN_FAIL                                 = "20000006";
    public static final String ERROR_MC_RESPONSE_DB_ERROR                                  = "20000007";


    public static final String ERROR_MC_RESPONSE_TXN_TYPE                                  = "21000000";
    public static final String ERROR_MC_RESPONSE_TXN_SUB_TYPE                              = "21000001";
    public static final String ERROR_MC_RESPONSE_CSUM                                      = "21000002";
    public static final String ERROR_MC_RESPONSE_IP                                        = "21000003";
    public static final String ERROR_MC_RESPONSE_TCODE                                     = "21000004";
    public static final String ERROR_MC_RESPONSE_ORDER_CODE_NONE                           = "21000005";
    public static final String ERROR_MC_RESPONSE_ORDER_PAY_SUCCESS                         = "21000006";
    public static final String ERROR_MC_RESPONSE_FRIST_PAY                                 = "21000007";
    public static final String ERROR_MC_RESPONSE_PAY_JNL_NOT_EXIST                         = "21000008";
    public static final String ERROR_MC_RESPONSE_PAY_JNL_NOT_UNIQUE                        = "21000009";
    public static final String ERROR_MC_RESPONSE_UPDATE_PAY_JNL_ERROR                      = "21000010";
    public static final String ERROR_MC_RESPONSE_ORDER_IS_NOT_EXIST                        = "21000011";
    public static final String ERROR_MC_RESPONSE_ORDER_IS_TIME_OUT                         = "21000012";
    public static final String ERROR_MC_RESPONSE_UPDATE_ORDER_JNL_ERROR                    = "21000013";
    public static final String ERROR_MC_RESPONSE_UPDATE_TRANS_LOG_JNL_ERROR                = "21000014";
    public static final String ERROR_MC_RESPONSE_CREATE_TRANS_LOG_JNL_ERROR                = "21000015";
    public static final String ERROR_MC_RESPONSE_CREATE_PAY_JNL_ERROR                      = "21000016";
    public static final String ERROR_MC_RESPONSE_CREATE_ORDER_JNL_ERROR                    = "21000017";
    public static final String ERROR_MC_RESPONSE_ORDER_IS_ALREADY_CREATED                  = "21000018";
    public static final String ERROR_MC_RESPONSE_TXNAMT_IS_NOT_EQUAL	                   = "21000019";
    public static final String ERROR_MC_RESPONSE_ACCESSMID_OR_ORDERCODE_NOT_EXIST	       = "21000020";
    public static final String ERROR_MC_RESPONSE_PAY_TOOL_NOT_MATCH	                       = "21000021";
    public static final String ERROR_MC_RESPONSE_ACQINSINFO_ERROR 	                       = "21000022";
    public static final String ERROR_MC_RESPONSE_SMPS_IS_NEED_PWD_ERROR 	               = "21000023";
    public static final String ERROR_MC_RESPONSE_SANDBAO_SACN_PAY_PAY_JNL_NOT_EXIST 	   = "21000024";
    public static final String ERROR_MC_RESPONSE_SANDBAO_SACN_PWD_FLAG_ERROR 	           = "21000025";
}

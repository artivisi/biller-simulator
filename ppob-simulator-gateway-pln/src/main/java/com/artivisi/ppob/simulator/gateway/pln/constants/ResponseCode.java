package com.artivisi.ppob.simulator.gateway.pln.constants;

public abstract class ResponseCode {
	public final static String SUCCESSFUL = "0000";
	public final static String ERROR_OTHER = "0005";
	public final static String ERROR_NEED_SIGN_ON = "0011";
	public final static String ERROR_SETTLEMENT_HAD_BEEN_DONE = "0012";
	public final static String ERROR_INVALID_TRANS_AMOUNT = "0013";
	public final static String ERROR_UNKNOWN_SUBSCRIBER = "0014";
	public final static String ERROR_INVALID_MESSAGE = "0030";
	public final static String ERROR_UNREGISTERED_BANK_CODE = "0031";
	public final static String ERROR_UNREGISTERED_SWITCHING = "0032";
	public final static String ERROR_UNREGISTERED_PRODUCT = "0033";
	public final static String ERROR_TRANSACTION_AMOUNT_BELOW_MINPURCHASE = "0041";
	public final static String ERROR_TRANSACTION_AMOUNT_EXCEED_MAXPURCHASE = "0042";
	public final static String ERROR_NO_PAYMENT = "0063";
	public final static String ERROR_TIMEOUT = "0068";
	public final static String ERROR_BILLS_ALREADY_PAID = "0088";
	public final static String ERROR_CURRENT_BILL_IS_NOT_AVAILABLE = "0089";
	public final static String ERROR_CUTOFF_INPROGRESS = "0090";
	public final static String ERROR_SWITCH_REF_NUM_NOT_AVAILABLE = "0092";
	public final static String ERROR_INVALID_SWITCH_REF_NUM = "0093";
	public final static String ERROR_REVERSAL_HAD_BEEN_DONE = "0094";
	public final static String ERROR_SWITCHID_OR_BANKCODE_NOT_IDENTICAL_WITH_PAY = "0097";
	public final static String ERROR_PLN_REFNUM_NOT_VALID = "0098";
}

package com.hz.constant;

public enum SmsContentType {
	 register(1),updatepwd(2),updatephone(3),bindbank(4),updateTradePwd(5),bindEmail(6),borrower(7);

    private int index;
    SmsContentType(int index) {
        this.index=index;
    }

    public int getIndex() {
        return index;
    }

    public static  SmsContentType valueOf(int index){
        switch (index){
            case 1:
                return register;
            case 2:
                return updatepwd;
            case 3:
                return updatephone;
            case 4:
                return bindbank;
            case 5:
                return updateTradePwd;
            case 6:
                return bindEmail;
            case 7:
            	return borrower;
            default:
                return null;
        }
    }
}

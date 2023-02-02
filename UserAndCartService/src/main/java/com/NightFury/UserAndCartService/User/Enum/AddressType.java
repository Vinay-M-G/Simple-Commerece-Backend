package com.NightFury.UserAndCartService.User.Enum;

public enum AddressType {
	
	BILLING{
		@Override
	    public String toString() {
	      return "BILLING";
	    }
	},
	
	SHIPPING{
		@Override
	    public String toString() {
	      return "SHIPPING";
	    }
	}
}

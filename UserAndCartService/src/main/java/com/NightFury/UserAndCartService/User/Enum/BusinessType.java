package com.NightFury.UserAndCartService.User.Enum;

public enum BusinessType {
	
	B2C{
		@Override
	    public String toString() {
	      return "B2C";
	    }
	},
	
	B2B{
		@Override
	    public String toString() {
	      return "B2B";
	    }
	}
}

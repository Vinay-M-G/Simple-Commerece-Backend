package com.NightFury.ProductService.Enum;

public enum StockStatus {
	
	IN_STOCK{
		@Override
	    public String toString() {
	      return "INS";
	    }
	},
	
	LOW_IN_STOCK{
		@Override
	    public String toString() {
	      return "LS";
	    }
	},
	
	OUT_OF_STOCK{
		@Override
	    public String toString() {
	      return "OS";
	    }
	}
}

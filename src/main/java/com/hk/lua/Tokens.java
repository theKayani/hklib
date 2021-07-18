package com.hk.lua;

interface Tokens
{
	// VALUES
	static final int T_NIL 				= 0x100;
	static final int T_STRING 			= 0x101;
	static final int T_IDENTIFIER 		= 0x102;
	static final int T_NUMBER	 		= 0x103;
	static final int T_BOOLEAN	 		= 0x104;
	static final int T_TABLE	 		= 0x105;
	static final int T_FUNCTION			= 0x106;
	static final int T_USERDATA			= 0x107;
	static final int T_THREAD			= 0x108;

	// SYMBOLS
	static final int T_PERIOD 			= 0x200;
	static final int T_COMMA 			= 0x201;
	static final int T_MODULO 			= 0x202;
	static final int T_POUND 			= 0x203;
	static final int T_POW 				= 0x204;
	static final int T_EQUALS 			= 0x205;
	static final int T_PLUS 			= 0x206;
	static final int T_MINUS 			= 0x207;
	static final int T_TIMES 			= 0x208;
	static final int T_DIVIDE 			= 0x209;
	static final int T_OPEN_BKT			= 0x20A;
	static final int T_CLSE_BKT			= 0x20B;
	static final int T_OPEN_BRC			= 0x20C;
	static final int T_CLSE_BRC 		= 0x20D;
	static final int T_OPEN_PTS			= 0x20E;
	static final int T_CLSE_PTS 		= 0x20F;
	static final int T_GRTR_THAN 		= 0x210;
	static final int T_LESS_THAN 		= 0x211;
	static final int T_GREQ_THAN 		= 0x212;
	static final int T_LSEQ_THAN 		= 0x213;
	static final int T_EEQUALS 			= 0x214;
	static final int T_NEQUALS 			= 0x215;
	static final int T_COLON 			= 0x216;
	static final int T_CONCAT 			= 0x217;
	static final int T_SEMIC 			= 0x218;
	static final int T_BAND	 			= 0x219;
	static final int T_BOR	 			= 0x21A;
	static final int T_BXOR				= 0x21B;
	static final int T_NEGATE 			= 0x21C;
	static final int T_NUMERIC_FOR		= 0x21D;
	static final int T_GENERIC_FOR		= 0x21E;
	static final int T_DOUBLECOLON		= 0x21F;
	static final int T_VARARGS 			= 0x220;
	static final int T_SHR				= 0x221;
	static final int T_SHL				= 0x222;
	static final int T_FLR_DIVIDE		= 0x223;
	static final int T_UBNOT			= 0x224;

	// KEYWORDS
	static final int T_IN				= 0x300;
	static final int T_NOT				= 0x301;
	static final int T_LOCAL			= 0x302;
	static final int T_BREAK			= 0x303;
	static final int T_END				= 0x304;
	static final int T_RETURN			= 0x305;
	static final int T_IF				= 0x306;
	static final int T_ELSEIF			= 0x307;
	static final int T_ELSE				= 0x308;
	static final int T_THEN				= 0x309;
	static final int T_DO				= 0x30A;
	static final int T_WHILE			= 0x30B;
	static final int T_FOR				= 0x30C;
	static final int T_REPEAT			= 0x30D;
	static final int T_UNTIL			= 0x30E;
	static final int T_AND				= 0x30F;
	static final int T_OR				= 0x310;
	static final int T_GOTO				= 0x311;
	
	// ERRORS
	static final int T_NULL 			= 0x400;
	static final int T_INC_STRING 		= 0x401;
	static final int T_INC_NUMBER 		= 0x402;
	
	// FLAGS
	static final int F_ELSEIF           = 0x0001;
	static final int F_ELSE             = 0x0002;
	static final int F_IF               = 0x0003;
	static final int F_DO               = 0x0004;
	static final int F_LOOP             = 0x0008;
	static final int F_WHILE            = 0x0018;
	static final int F_FOR              = 0x0028;
	static final int F_REPEAT           = 0x0048;
	static final int F_FUNCTION         = 0x0080;
}

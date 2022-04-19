package com.hk.lua;

/**
 * Various token identifiers and flags
 */
public interface Tokens
{
	// VALUES
	/** Constant <code>T_NIL=0x100</code> */
	int T_NIL 				= 0x100;
	/** Constant <code>T_STRING=0x101</code> */
	int T_STRING 			= 0x101;
	/** Constant <code>T_IDENTIFIER=0x102</code> */
	int T_IDENTIFIER 		= 0x102;
	/** Constant <code>T_NUMBER=0x103</code> */
	int T_NUMBER	 		= 0x103;
	/** Constant <code>T_BOOLEAN=0x104</code> */
	int T_BOOLEAN	 		= 0x104;
	/** Constant <code>T_TABLE=0x105</code> */
	int T_TABLE	 		= 0x105;
	/** Constant <code>T_FUNCTION=0x106</code> */
	int T_FUNCTION			= 0x106;
	/** Constant <code>T_USERDATA=0x107</code> */
	int T_USERDATA			= 0x107;
	/** Constant <code>T_THREAD=0x108</code> */
	int T_THREAD			= 0x108;

	// SYMBOLS
	/** Constant <code>T_PERIOD=0x200</code> */
	int T_PERIOD 			= 0x200;
	/** Constant <code>T_COMMA=0x201</code> */
	int T_COMMA 			= 0x201;
	/** Constant <code>T_MODULO=0x202</code> */
	int T_MODULO 			= 0x202;
	/** Constant <code>T_POUND=0x203</code> */
	int T_POUND 			= 0x203;
	/** Constant <code>T_POW=0x204</code> */
	int T_POW 				= 0x204;
	/** Constant <code>T_EQUALS=0x205</code> */
	int T_EQUALS 			= 0x205;
	/** Constant <code>T_PLUS=0x206</code> */
	int T_PLUS 			= 0x206;
	/** Constant <code>T_MINUS=0x207</code> */
	int T_MINUS 			= 0x207;
	/** Constant <code>T_TIMES=0x208</code> */
	int T_TIMES 			= 0x208;
	/** Constant <code>T_DIVIDE=0x209</code> */
	int T_DIVIDE 			= 0x209;
	/** Constant <code>T_OPEN_BKT=0x20A</code> */
	int T_OPEN_BKT			= 0x20A;
	/** Constant <code>T_CLSE_BKT=0x20B</code> */
	int T_CLSE_BKT			= 0x20B;
	/** Constant <code>T_OPEN_BRC=0x20C</code> */
	int T_OPEN_BRC			= 0x20C;
	/** Constant <code>T_CLSE_BRC=0x20D</code> */
	int T_CLSE_BRC 		= 0x20D;
	/** Constant <code>T_OPEN_PTS=0x20E</code> */
	int T_OPEN_PTS			= 0x20E;
	/** Constant <code>T_CLSE_PTS=0x20F</code> */
	int T_CLSE_PTS 		= 0x20F;
	/** Constant <code>T_GRTR_THAN=0x210</code> */
	int T_GRTR_THAN 		= 0x210;
	/** Constant <code>T_LESS_THAN=0x211</code> */
	int T_LESS_THAN 		= 0x211;
	/** Constant <code>T_GREQ_THAN=0x212</code> */
	int T_GREQ_THAN 		= 0x212;
	/** Constant <code>T_LSEQ_THAN=0x213</code> */
	int T_LSEQ_THAN 		= 0x213;
	/** Constant <code>T_EEQUALS=0x214</code> */
	int T_EEQUALS 			= 0x214;
	/** Constant <code>T_NEQUALS=0x215</code> */
	int T_NEQUALS 			= 0x215;
	/** Constant <code>T_COLON=0x216</code> */
	int T_COLON 			= 0x216;
	/** Constant <code>T_CONCAT=0x217</code> */
	int T_CONCAT 			= 0x217;
	/** Constant <code>T_SEMIC=0x218</code> */
	int T_SEMIC 			= 0x218;
	/** Constant <code>T_BAND=0x219</code> */
	int T_BAND	 			= 0x219;
	/** Constant <code>T_BOR=0x21A</code> */
	int T_BOR	 			= 0x21A;
	/** Constant <code>T_BXOR=0x21B</code> */
	int T_BXOR				= 0x21B;
	/** Constant <code>T_NEGATE=0x21C</code> */
	int T_NEGATE 			= 0x21C;
	/** Constant <code>T_NUMERIC_FOR=0x21D</code> */
	int T_NUMERIC_FOR		= 0x21D;
	/** Constant <code>T_GENERIC_FOR=0x21E</code> */
	int T_GENERIC_FOR		= 0x21E;
	/** Constant <code>T_DOUBLECOLON=0x21F</code> */
	int T_DOUBLECOLON		= 0x21F;
	/** Constant <code>T_VARARGS=0x220</code> */
	int T_VARARGS 			= 0x220;
	/** Constant <code>T_SHR=0x221</code> */
	int T_SHR				= 0x221;
	/** Constant <code>T_SHL=0x222</code> */
	int T_SHL				= 0x222;
	/** Constant <code>T_FLR_DIVIDE=0x223</code> */
	int T_FLR_DIVIDE		= 0x223;
	/** Constant <code>T_UBNOT=0x224</code> */
	int T_UBNOT			= 0x224;

	// KEYWORDS
	/** Constant <code>T_IN=0x300</code> */
	int T_IN				= 0x300;
	/** Constant <code>T_NOT=0x301</code> */
	int T_NOT				= 0x301;
	/** Constant <code>T_LOCAL=0x302</code> */
	int T_LOCAL			= 0x302;
	/** Constant <code>T_BREAK=0x303</code> */
	int T_BREAK			= 0x303;
	/** Constant <code>T_END=0x304</code> */
	int T_END				= 0x304;
	/** Constant <code>T_RETURN=0x305</code> */
	int T_RETURN			= 0x305;
	/** Constant <code>T_IF=0x306</code> */
	int T_IF				= 0x306;
	/** Constant <code>T_ELSEIF=0x307</code> */
	int T_ELSEIF			= 0x307;
	/** Constant <code>T_ELSE=0x308</code> */
	int T_ELSE				= 0x308;
	/** Constant <code>T_THEN=0x309</code> */
	int T_THEN				= 0x309;
	/** Constant <code>T_DO=0x30A</code> */
	int T_DO				= 0x30A;
	/** Constant <code>T_WHILE=0x30B</code> */
	int T_WHILE			= 0x30B;
	/** Constant <code>T_FOR=0x30C</code> */
	int T_FOR				= 0x30C;
	/** Constant <code>T_REPEAT=0x30D</code> */
	int T_REPEAT			= 0x30D;
	/** Constant <code>T_UNTIL=0x30E</code> */
	int T_UNTIL			= 0x30E;
	/** Constant <code>T_AND=0x30F</code> */
	int T_AND				= 0x30F;
	/** Constant <code>T_OR=0x310</code> */
	int T_OR				= 0x310;
	/** Constant <code>T_GOTO=0x311</code> */
	int T_GOTO				= 0x311;

	// ERRORS
	/** Constant <code>T_NULL=0x400</code> */
	int T_NULL 			= 0x400;
	/** Constant <code>T_INC_STRING=0x401</code> */
	int T_INC_STRING 		= 0x401;
	/** Constant <code>T_INC_NUMBER=0x402</code> */
	int T_INC_NUMBER 		= 0x402;

	// FLAGS
	/** Constant <code>F_ELSEIF=0x0001</code> */
	int F_ELSEIF           = 0x0001;
	/** Constant <code>F_ELSE=0x0002</code> */
	int F_ELSE             = 0x0002;
	/** Constant <code>F_IF=0x0003</code> */
	int F_IF               = 0x0003;
	/** Constant <code>F_DO=0x0004</code> */
	int F_DO               = 0x0004;
	/** Constant <code>F_LOOP=0x0008</code> */
	int F_LOOP             = 0x0008;
	/** Constant <code>F_WHILE=0x0018</code> */
	int F_WHILE            = 0x0018;
	/** Constant <code>F_FOR=0x0028</code> */
	int F_FOR              = 0x0028;
	/** Constant <code>F_REPEAT=0x0048</code> */
	int F_REPEAT           = 0x0048;
	/** Constant <code>F_FUNCTION=0x0080</code> */
	int F_FUNCTION         = 0x0080;
}
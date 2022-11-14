package com.hk.blok;

import java.io.IOException;

public class Blok
{
	static Blok readBlok(Tokenizer tkz, int flags) throws IOException
	{
		while (tkz.next())
		{
			System.out.println('"' + tkz.token() + "\" " + tkz.type());
			System.out.println("\t" + tkz.value());
		}

		return null;
	}
}
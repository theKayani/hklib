package com.hk.blok;

import com.hk.blok.Tokenizer.Tokens;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Bloks
{
	public static Blok read(Reader rdr) throws BlokFormatException
	{
		try
		{
			Tokenizer tkz = new Tokenizer(rdr);
			Blok blok = Blok.readBlok(tkz, Tokens.F_GLOBAL);

			if(tkz.next())
				throw new BlokFormatException("Unexpected character: " + tkz.token());

			tkz.close();

			return blok;
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	public static Blok read(File f) throws FileNotFoundException
	{
		return read(new FileReader(f));
	}
}

/**
 * This class file was automatically generated by jASN1 v1.7.1 (http://www.openmuc.org)
 */

package org.openmuc.jasn1.compiler.pkix1explicit88;

import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import org.openmuc.jasn1.ber.*;
import org.openmuc.jasn1.ber.types.*;
import org.openmuc.jasn1.ber.types.string.*;

import org.openmuc.jasn1.compiler.pkix1implicit88.*;
import org.openmuc.jasn1.compiler.rspdefinitions.*;

public class X520OrganizationalUnitName {

	public byte[] code = null;
	public BerTeletexString teletexString = null;
	public BerPrintableString printableString = null;
	public BerUniversalString universalString = null;
	public BerUTF8String utf8String = null;
	public BerBMPString bmpString = null;
	
	public X520OrganizationalUnitName() {
	}

	public X520OrganizationalUnitName(byte[] code) {
		this.code = code;
	}

	public X520OrganizationalUnitName(BerTeletexString teletexString, BerPrintableString printableString, BerUniversalString universalString, BerUTF8String utf8String, BerBMPString bmpString) {
		this.teletexString = teletexString;
		this.printableString = printableString;
		this.universalString = universalString;
		this.utf8String = utf8String;
		this.bmpString = bmpString;
	}

	public int encode(BerByteArrayOutputStream os) throws IOException {

		if (code != null) {
			for (int i = code.length - 1; i >= 0; i--) {
				os.write(code[i]);
			}
			return code.length;
		}

		int codeLength = 0;
		if (bmpString != null) {
			codeLength += bmpString.encode(os, true);
			return codeLength;
		}
		
		if (utf8String != null) {
			codeLength += utf8String.encode(os, true);
			return codeLength;
		}
		
		if (universalString != null) {
			codeLength += universalString.encode(os, true);
			return codeLength;
		}
		
		if (printableString != null) {
			codeLength += printableString.encode(os, true);
			return codeLength;
		}
		
		if (teletexString != null) {
			codeLength += teletexString.encode(os, true);
			return codeLength;
		}
		
		throw new IOException("Error encoding BerChoice: No item in choice was selected.");
	}

	public int decode(InputStream is) throws IOException {
		return decode(is, null);
	}

	public int decode(InputStream is, BerTag berTag) throws IOException {

		int codeLength = 0;
		BerTag passedTag = berTag;

		if (berTag == null) {
			berTag = new BerTag();
			codeLength += berTag.decode(is);
		}

		if (berTag.equals(BerTeletexString.tag)) {
			teletexString = new BerTeletexString();
			codeLength += teletexString.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerPrintableString.tag)) {
			printableString = new BerPrintableString();
			codeLength += printableString.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerUniversalString.tag)) {
			universalString = new BerUniversalString();
			codeLength += universalString.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerUTF8String.tag)) {
			utf8String = new BerUTF8String();
			codeLength += utf8String.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerBMPString.tag)) {
			bmpString = new BerBMPString();
			codeLength += bmpString.decode(is, false);
			return codeLength;
		}

		if (passedTag != null) {
			return 0;
		}

		throw new IOException("Error decoding BerChoice: Tag matched to no item.");
	}

	public void encodeAndSave(int encodingSizeGuess) throws IOException {
		BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
		encode(os);
		code = os.getArray();
	}

	public String toString() {
		if (teletexString != null) {
			return "CHOICE{teletexString: " + teletexString + "}";
		}

		if (printableString != null) {
			return "CHOICE{printableString: " + printableString + "}";
		}

		if (universalString != null) {
			return "CHOICE{universalString: " + universalString + "}";
		}

		if (utf8String != null) {
			return "CHOICE{utf8String: " + utf8String + "}";
		}

		if (bmpString != null) {
			return "CHOICE{bmpString: " + bmpString + "}";
		}

		return "unknown";
	}

}

